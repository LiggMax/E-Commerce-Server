/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service;

import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.PayDto;
import com.ligg.common.module.vo.OrderInfoVo;
import jakarta.validation.constraints.NotNull;

public interface OrderService {

    /**
     * 创建订单
     */
    String createOrder(OrderDto orderDto);

    /**
     * 获取订单信息
     */
    OrderInfoVo getOrderInfo(String orderNo);

    /**
     * 获取订单过期时间
     */
    Long getOrderExpireTime(String orderNo);

    /**
     * 支付订单
     */
    void payOrder(@NotNull PayDto pay);
}
