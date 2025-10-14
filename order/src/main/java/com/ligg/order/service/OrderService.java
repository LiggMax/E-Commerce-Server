/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service;

import com.ligg.common.module.dto.OrderDto;

public interface OrderService {

    /**
     * 创建订单
     */
    String createOrder(OrderDto orderDto);
}
