/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service;

import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.OrderInfoDto;

public interface OrderService {

    /**
     * 校验是否有订单还未付款
     */
    void checkOrder();

    /**
     * 创建订单
     */
    String createOrder(OrderDto orderDto);

    /**
     * 获取订单信息
     */
    OrderInfoDto getOrderInfo(String orderId);

    /**
     * 支付订单
     */
    String payOrder(String orderId);
}
