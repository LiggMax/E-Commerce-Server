/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service.impl;

import com.ligg.common.module.dto.OrderDto;
import com.ligg.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 创建订单
     */
    @Override
    public synchronized boolean createOrder(OrderDto orderDto) {
        return true;
    }
}
