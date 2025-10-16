/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.constants.OrderConstant;
import com.ligg.common.constants.ProductConstant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.mapper.ProductMapper;
import com.ligg.common.mapper.ProductSpecMapper;
import com.ligg.common.mapper.SpecValueMapper;
import com.ligg.common.mapper.order.OrderItemMapper;
import com.ligg.common.mapper.order.OrderItemSpecMapper;
import com.ligg.common.mapper.order.OrderMapper;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.entity.*;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.order.service.OrderService;
import com.ligg.order.service.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final SpecValueMapper specValueMapper;

    private final ProductMapper productMapper;

    private final ProductSpecMapper productSpecMapper;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final OrderItemSpecMapper orderItemSpecMapper;

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
    public synchronized String createOrder(OrderDto orderDto) {
        String orderLockKey = OrderConstant.ORDER_LOCK_KEY + orderDto.getProductId();
        Boolean lockSuccess = redisTemplate.opsForValue().setIfAbsent(orderLockKey, true, 10, TimeUnit.SECONDS);

        if (Boolean.FALSE.equals(lockSuccess)) {
            throw new OrderException("请勿重复下单");
        }

        try {
            String stockKey = ProductConstant.STOCK_KEY_PREFIX + orderDto.getProductId();
            int quantity = orderDto.getQuantity() == null ? 1 : orderDto.getQuantity();
            if (quantity <= 0) {
                quantity = 1;
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
            long result = redisTemplate.execute(DECR_STOCK_SCRIPT, Collections.singletonList(stockKey), quantity);
            if (result == -2L) {
                throw new OrderException("库存信息不存在");
            }
            if (result == -1L) {
                throw new OrderException("库存不足,无法下单");
            }

            // 从这里开始，如果后续任意一步失败，需要回滚库存
            try {
                // 获取商品详情
                ProductEntity productDetail = getProductDetail(orderDto.getProductId());

                //创建订单实体
                OrderEntity order = new OrderEntity();

                String orderNo = UUID.randomUUID().toString();
                Map<String, Object> userObject = ThreadLocalUtil.get();
                String userId = (String) userObject.get(UserConstant.USER_ID);

                //构建订单数据
                order.setOrderNo(orderNo);
                order.setUserId(userId);
                order.setPayType(orderDto.getPayType());
                order.setStatus(OrderStatus.UNPAID);
                order.setAddressId(orderDto.getAddressId());
                order.setRemark(orderDto.getRemark());
                order.setCreateTime(LocalDateTime.now());
                order.setPayTime(LocalDateTime.now());

                // 计算商品总价
                Double currentPrice = productDetail.getCurrentPrice();
                Integer proDuctQuantity = orderDto.getQuantity();
                //公式 商品总价 = (商品单价 + 规格总价) * 商品数量
                order.setTotalAmount((currentPrice + specValuetotalAmount) * proDuctQuantity);

                //保存订单
                if (orderMapper.insert(order) < 1) {
                    throw new OrderException("保存订单信息失败");
                }

                //保存订单详情
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrderId(order.getId());
                orderItem.setProductId(orderDto.getProductId());
                orderItem.setQuantity(orderDto.getQuantity());
                orderItem.setSubtotal(currentPrice * proDuctQuantity);
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

                //返回订单id
                return orderNo;
            } catch (RuntimeException ex) {
                // 回滚库存
                redisTemplate.opsForValue().increment(stockKey, quantity);
                throw ex;
            }
        } finally {
            redisTemplate.delete(orderLockKey);
        }
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
