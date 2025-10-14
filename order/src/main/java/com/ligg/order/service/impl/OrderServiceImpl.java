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
import com.ligg.common.mapper.SpecValueMapper;
import com.ligg.common.mapper.order.OrderItemMapper;
import com.ligg.common.mapper.order.OrderItemSpecMapper;
import com.ligg.common.mapper.order.OrderMapper;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.entity.*;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final SpecValueMapper specValueMapper;

    private final ProductMapper productMapper;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final OrderItemSpecMapper orderItemSpecMapper;

    /**
     * 创建订单
     */
    @Override
    public synchronized String createOrder(OrderDto orderDto) {
        String orderLockKey = OrderConstant.ORDER_LOCK_KEY + orderDto.getProductId();
        Boolean lockSuccess = redisTemplate.opsForValue().setIfAbsent(orderLockKey, true, 10, TimeUnit.SECONDS);

        if (Boolean.FALSE.equals(lockSuccess)) {
            throw new RuntimeException("请勿重复下单");
        }

        try {
            String stockKey = ProductConstant.STOCK_KEY_PREFIX + orderDto.getProductId();
            Long stock = redisTemplate.opsForValue().decrement(stockKey);

            if (stock == null) {
                throw new RuntimeException("库存信息不存在");
            }

            if (stock < 0) {
                // 回滚库存
                redisTemplate.opsForValue().increment(stockKey);
                throw new RuntimeException("库存不足,无法下单");
            }
            //创建订单实体
            OrderEntity order = new OrderEntity();

            //保存订单详情
            OrderEntity orderEntity = buildOrder(orderDto);

            //构建订单数据
            order.setOrderNo(orderEntity.getOrderNo());
            order.setUserId(orderEntity.getUserId());
            order.setPayType(orderDto.getPayType());
            order.setOrderStatus(OrderStatus.UNPAID);
            order.setAddressId(orderDto.getAddressId());
            order.setRemark(orderDto.getRemark());
            order.setCreateTime(LocalDateTime.now());
            order.setPayTime(LocalDateTime.now());

            Double totalAmount = 0.0;
            List<OrderDto.SpecDto> specDto = new OrderDto().getSpec();
            for (OrderDto.SpecDto dto : specDto) {
                SpecValueEntity specValue = getSpecValue(dto.getId());
                totalAmount += specValue.getPrice();
            }
            order.setTotalAmount(totalAmount * orderDto.getQuantity());


            //保存订单
            if (orderMapper.insert(order) < 1) {
                throw new RuntimeException("保存订单信息失败");
            }
            //返回订单id
            return orderEntity.getOrderNo();
        } finally {
            redisTemplate.delete(orderLockKey);
        }
    }

    /**
     * 保存订单详情
     */
    private OrderEntity buildOrder(OrderDto orderDto) {
        OrderEntity order = new OrderEntity();

        //构建订单号&用户id信息
        String orderNo = UUID.randomUUID().toString();
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(UserConstant.USER_ID);
        order.setOrderNo(orderNo);
        order.setUserId(userId);

        ProductEntity productEntity = productMapper.selectById(orderDto.getProductId());

        //保存商品详情
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(orderDto.getProductId());
        orderItem.setQuantity(orderDto.getQuantity());
        //TODO 临时计算方案，这样计算不准确，因为价格没有+选择规格值的加价
        orderItem.setSubtotal(productEntity.getCurrentPrice());
        saveOrderItem(orderItem);

        //保存订单规格详情
        List<OrderDto.SpecDto> specs = orderDto.getSpec();

        specs.forEach(spec -> {
            OrderItemSpecEntity orderItemSpec = new OrderItemSpecEntity();
            orderItemSpec.setOrderItemId(orderItem.getId());
            orderItemSpec.setSpecValueId(spec.getSpecValue().getId());
            saveOrderItemSpec(orderItemSpec);
        });

        return order;
    }

    /**
     * 查询规格内容金额
     */
    public SpecValueEntity getSpecValue(Integer specId) {
        SpecValueEntity specValueEntity = specValueMapper.selectOne(
                new LambdaQueryWrapper<SpecValueEntity>().eq(SpecValueEntity::getSpecId, specId)
        );
        if (specValueEntity == null) {
            throw new RuntimeException("规格内容不存在");
        }
        return specValueEntity;
    }

    /**
     * 保存订单详情信息
     */
    public void saveOrderItem(OrderItemEntity orderItem) {
        if (orderItemMapper.insert(orderItem) < 1) {
            throw new RuntimeException("保存订单详情信息失败");
        }
    }

    /**
     * 保存订单规格信息
     */
    public void saveOrderItemSpec(OrderItemSpecEntity orderItemSpec) {
        if (orderItemSpecMapper.insert(orderItemSpec) < 1) {
            throw new RuntimeException("保存订单规格信息失败");
        }
    }
}
