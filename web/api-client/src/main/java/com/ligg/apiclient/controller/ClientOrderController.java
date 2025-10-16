/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.utils.Response;
import com.ligg.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控接口
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/order")
public class ClientOrderController {

    private final OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    public Response<String> createOrder(@Validated @RequestBody OrderDto orderDto) {
        // 校验是否有订单未完成支付
        orderService.checkOrder();
        String orderNo = orderService.createOrder(orderDto);
        return Response.success(BusinessStates.SUCCESS, orderNo);
    }
}
