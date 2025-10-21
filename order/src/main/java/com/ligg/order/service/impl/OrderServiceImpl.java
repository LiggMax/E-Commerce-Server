/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.common.constants.OrderConstant;
import com.ligg.common.constants.ProductConstant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.mapper.ProductMapper;
import com.ligg.common.mapper.ProductSpecMapper;
import com.ligg.common.mapper.SpecValueMapper;
import com.ligg.common.mapper.order.OrderItemMapper;
import com.ligg.common.mapper.order.OrderItemSpecMapper;
import com.ligg.common.mapper.order.OrderMapper;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.PayDto;
import com.ligg.common.module.entity.*;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.module.vo.OrderVo;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.order.service.OrderService;
import com.ligg.common.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final UserService userService;

    private final ProductMapper productMapper;

    private final OrderItemMapper orderItemMapper;

    private final SpecValueMapper specValueMapper;

    private final ProductSpecMapper productSpecMapper;

    private final OrderItemSpecMapper orderItemSpecMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    // 使用 Lua 脚本原子校验并按数量扣减库存：
    // 返回值约定：>=0 表示扣减后库存，-1 表示库存不足，-2 表示库存键不存在
    private static final DefaultRedisScript<Long> DECR_STOCK_SCRIPT = new DefaultRedisScript<>();

    static {
        DECR_STOCK_SCRIPT.setLocation(new ClassPathResource("redis.lua"));
        DECR_STOCK_SCRIPT.setResultType(Long.class);
    }

    /**
     * 创建订单
     */
    @Override
    public String createOrder(OrderDto orderDto) {
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(UserConstant.USER_ID);
        String userOrderLockKey = OrderConstant.ORDER_LOCK_KEY + orderDto.getProductId();
        Boolean userLockSuccess = redisTemplate.opsForValue().setIfAbsent(userOrderLockKey, true, 10, TimeUnit.SECONDS);
        long waitTime = 0;
        try {

            // 用户锁，防止重复下单
            if (Boolean.FALSE.equals(userLockSuccess)) {
                // 等待锁释放，最多等待10秒
                while (waitTime < 10000) { // 最多等待10秒
                    try {
                        Thread.sleep(100); // 每100毫秒检查一次
                        waitTime += 100;
                        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(userOrderLockKey, true, 10, TimeUnit.SECONDS);
                        if (Boolean.TRUE.equals(isLocked)) {
                            userLockSuccess = true;
                            break;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new OrderException("下单过程中发生中断");
                    }
                }
                // 等待超时
                if (!userLockSuccess) {
                    throw new OrderException("系统繁忙，请稍后再试");
                }
            }

            OrderEntity redisOrder = (OrderEntity) redisTemplate.opsForValue().get(UserConstant.USER + ':' + OrderConstant.ORDER_KEY + userId);
            if (redisOrder != null) {
                throw new OrderException("您有一笔订单未完成支付");
            }

            /*
             * 先做规格校验与金额计算，任何失败都不会触发库存变更
             * 当规格不为空时才获取规格内容金额。
             */
            Double specValuetotalAmount = 0.0;
            List<OrderDto.SpecDto> specDto = orderDto.getSpec();
            if (specDto != null && !specDto.isEmpty()) {
                for (OrderDto.SpecDto dto : specDto) {
                    ProductSpecValueEntity specValue = getSpecValue(dto.getSpecValue().getId(), orderDto.getProductId());
                    specValuetotalAmount += specValue.getPrice();
                }
            }

            // 规格校验通过后，再原子扣减库存
            String stockKey = ProductConstant.STOCK_KEY_PREFIX + orderDto.getProductId();
            int quantity = orderDto.getQuantity();

            long result = redisTemplate.execute(DECR_STOCK_SCRIPT, Collections.singletonList(stockKey), quantity);
            if (result == -2L) {
                throw new OrderException("库存信息不存在");
            }
            if (result == -1L) {
                throw new OrderException("库存不足,无法下单");
            }

            // 从这里开始，如果后续任意一步失败，需要回滚库存
            try {
                //扣减商品数据库库存
                productMapper.updateStockDeduct(orderDto.getProductId(), quantity);
                // 获取商品详情
                ProductEntity productDetail = getProductDetail(orderDto.getProductId());

                //创建订单实体
                OrderEntity order = new OrderEntity();

                String orderNo = UUID.randomUUID().toString();

                //构建订单数据
                order.setOrderNo(orderNo);
                order.setUserId(userId);
                order.setAddressId(orderDto.getAddressId());
                order.setStatus(OrderStatus.UNPAID);
                order.setRemark(orderDto.getRemark());
                order.setPayType(orderDto.getPayType());
                order.setCreateTime(LocalDateTime.now());

                // 计算商品总价
                BigDecimal productPrice = BigDecimal.valueOf(productDetail.getCurrentPrice());
                BigDecimal specTotalAmount = BigDecimal.valueOf(specValuetotalAmount);
                BigDecimal productQuantity = BigDecimal.valueOf(orderDto.getQuantity());

                BigDecimal totalAmount = productPrice.add(specTotalAmount).multiply(productQuantity);
                //公式 商品总价 = (商品单价 + 规格总价) * 商品数量
                order.setTotalAmount(totalAmount);

                //保存订单
                if (orderMapper.insert(order) < 1) {
                    throw new OrderException("保存订单信息失败");
                }

                //保存订单详情
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrderId(order.getId());
                orderItem.setProductId(orderDto.getProductId());
                orderItem.setQuantity(orderDto.getQuantity());
                orderItem.setSubtotal(productPrice.multiply(productQuantity));
                saveOrderItem(orderItem);

                //规格部位空时，保存订单规格详情
                if (specDto != null && !specDto.isEmpty()) {
                    for (OrderDto.SpecDto spec : specDto) {
                        OrderItemSpecEntity orderItemSpec = new OrderItemSpecEntity();
                        orderItemSpec.setOrderItemId(orderItem.getId());
                        orderItemSpec.setSpecValueId(spec.getSpecValue().getId());
                        saveOrderItemSpec(orderItemSpec);
                    }
                }

                //订单放入缓存
                String orderKey = UserConstant.USER + ':' + OrderConstant.ORDER_KEY + userId + ':' + orderNo;
                redisTemplate.opsForValue().set(orderKey, order, 1, TimeUnit.HOURS); //订单有效期1小时
                //返回订单id
                return orderNo;
            } catch (RuntimeException ex) {
                // 回滚库存
                redisTemplate.opsForValue().increment(stockKey, quantity);
                productMapper.updateStockAdd(orderDto.getProductId(), quantity);
                throw ex;
            }
        } finally {
            redisTemplate.delete(userOrderLockKey);
        }
    }

    /**
     * 获取订单信息
     */
    @Override
    public OrderInfoVo getOrderInfo(String orderNo) {
        OrderInfoVo orderInfo = orderMapper.selectOrderByOrderNo(orderNo);
        List<OrderInfoVo.SpecValue> specValues = orderMapper.selectOrderItemSpecListByOrderNo(orderNo);

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfoVo.setSpecValues(specValues);
        return orderInfoVo;
    }

    @Override
    public Long getOrderExpireTime(String orderNo) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        String orderKey = UserConstant.USER + ':' + OrderConstant.ORDER_KEY + userId + ':' + orderNo;
        return redisTemplate.getExpire(orderKey, TimeUnit.SECONDS);
    }

    /**
     * 支付订单
     */
    @Override
    @Transactional
    public void payOrder(PayDto pay) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        String userOrderLockKey = OrderConstant.ORDER_PAY_LOCK_KEY + pay.getOrderNo();
        String orderKey = UserConstant.USER + ':' + OrderConstant.ORDER_KEY + userId + ':' + pay.getOrderNo();

        try {
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(userOrderLockKey, true, 10, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(locked)) {
                throw new OrderException("订单正在处理中,请勿重复操作");
            }

            OrderEntity orderInfo = (OrderEntity) redisTemplate.opsForValue().get(orderKey);
            if (orderInfo == null) {
                throw new OrderException("订单不存在");
            }

            if (orderInfo.getStatus() != OrderStatus.UNPAID) {
                throw new OrderException("订单已支付或也取消");
            }

            //扣减用户余额
            orderInfo.setPayType(pay.getPayType());
            userService.debit(orderInfo.getTotalAmount());
            //修改订单状态为已支付
            updateOrderStatus(orderInfo);
            //删除用户订单缓存
            redisTemplate.delete(orderKey);
        } finally {
            //释放锁
            redisTemplate.delete(userOrderLockKey);
        }
    }

    /**
     * 获取用户订单列表
     */
    @Override
    public PageVo<OrderVo> getUserOrderList(Long pageNum, Long pageSize) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);

        IPage<OrderVo> page = new Page<>(pageNum, pageSize);
        IPage<OrderVo> result = orderMapper.selectOrderListByUserId(page, userId);

        PageVo<OrderVo> orderList = new PageVo<>();
        orderList.setPages(result.getPages());
        orderList.setTotal(result.getTotal());
        orderList.setList(result.getRecords());
        return orderList;
    }

    /**
     * 查询商品详情
     */
    public ProductEntity getProductDetail(Long productId) {
        ProductEntity productEntity = productMapper.selectById(productId);
        if (productEntity == null) {
            throw new OrderException("商品不存在");
        }
        return productEntity;
    }

    /**
     * 查询规格内容金额
     */
    public ProductSpecValueEntity getSpecValue(Integer specValueId, Long productId) {
        List<ProductSpecEntity> productSpecEntities = productSpecMapper.selectList(
                new LambdaQueryWrapper<ProductSpecEntity>().eq(ProductSpecEntity::getProductId, productId)
        );
        if (productSpecEntities == null || productSpecEntities.isEmpty()) {
            throw new OrderException("商品规格不存在");
        }
        ProductSpecValueEntity specValueEntity = specValueMapper.selectById(specValueId);
        if (specValueEntity == null) {
            throw new OrderException("规格内容不存在");
        }
        return specValueEntity;
    }

    /**
     * 更新订单状态为已支付
     */
    public void updateOrderStatus(OrderEntity order) {
        if (orderMapper.update(new LambdaUpdateWrapper<OrderEntity>()
                .eq(OrderEntity::getId, order.getId())
                .set(OrderEntity::getPayTime, LocalDateTime.now())
                .set(OrderEntity::getUpdateTime, LocalDateTime.now())
                .set(OrderEntity::getPayType, order.getPayType())
                .set(OrderEntity::getStatus, OrderStatus.PAID)) < 1)
            throw new OrderException(BusinessStates.INTERNAL_SERVER_ERROR.getMessage());
    }

    /**
     * 保存订单详情信息
     */
    public void saveOrderItem(OrderItemEntity orderItem) {
        if (orderItemMapper.insert(orderItem) < 1) {
            throw new OrderException("保存订单详情信息失败");
        }
    }

    /**
     * 保存订单规格信息
     */
    public void saveOrderItemSpec(OrderItemSpecEntity orderItemSpec) {
        if (orderItemSpecMapper.insert(orderItemSpec) < 1) {
            throw new OrderException("保存订单规格信息失败");
        }
    }
}
