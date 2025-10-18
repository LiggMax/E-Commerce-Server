/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.apiclient.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.OrderInfoDto;
import com.ligg.common.module.dto.PayDto;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.module.vo.Views;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.order.service.OrderService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * 订单控接口
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/user/order")
public class ClientOrderController {

    private final OrderService orderService;

    private final UserService userService;

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

    /**
     * 获取订单信息
     * @param orderNo 订单号
     */
    @GetMapping("/info")
    @JsonView(Views.SimpleView.class)
    public Response<OrderInfoVo> getOrderInfo(@NotNull String orderNo) {
        OrderInfoDto orderInfo = orderService.getOrderInfo(orderNo);

        // 检查查询结果是否为空
        if (orderInfo == null) {
            return Response.error(BusinessStates.NOT_FOUND, "订单不存在");
        }

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setCreateTime(orderInfo.getCreateTime());
        orderInfoVo.setOrderNo(orderInfo.getOrderNo());
        orderInfoVo.setAddressId(orderInfo.getAddressId());
        orderInfoVo.setRemark(orderInfo.getRemark());
        orderInfoVo.setStatus(orderInfo.getStatus());
        orderInfoVo.setTotalAmount(orderInfo.getTotalAmount());
        System.out.println(orderInfo);
        return Response.success(BusinessStates.SUCCESS, orderInfoVo);
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay")
    public Response<String> payOrder(@RequestBody @Validated PayDto pay) {
        orderService.payOrder(pay.getOrderNo());
        return Response.success(BusinessStates.SUCCESS);
    }
}
