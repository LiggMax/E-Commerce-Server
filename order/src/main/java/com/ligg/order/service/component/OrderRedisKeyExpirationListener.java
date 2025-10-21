/**
 * @author Ligg
 * @Time 2025/10/21
 **/
package com.ligg.order.service.component;

import com.ligg.common.constants.OrderConstant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.entity.OrderEntity;
import com.ligg.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final OrderService orderService;

    public OrderRedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, OrderService orderService) {
        super(listenerContainer);
        this.orderService = orderService;
    }

    /**
     * 监听key过期事件
     *
     * @param message 过期的key
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        if (!expiredKey.startsWith(UserConstant.USER + ':' + OrderConstant.ORDER_KEY)) {
            return;
        }
        String[] parts = expiredKey.split(":");
        handleOrderExpire(parts[2]);
    }

    /**
     * 订单过期事件处理
     */
    private void handleOrderExpire(String orderNo) {
        OrderEntity orderInfo = orderService.getOrderByOderNo(orderNo);
        if (orderInfo == null) {
            log.error("过期的订单不存在:{}", orderNo);
            throw new RuntimeException("过期的订单不存在");
        }
        if (orderInfo.getStatus() != OrderStatus.UNPAID) {
            log.error("当前订单状态:{}", orderInfo.getStatus());
            throw new RuntimeException("订单状态异常");
        }
        orderService.cancelOrder(orderInfo.getId());
        log.info("订单:{{}}过期未支付已取消", orderNo);
    }
}
