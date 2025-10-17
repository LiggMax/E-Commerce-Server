/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.OrderInfoDto;
import com.ligg.common.module.dto.PayDto;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.order.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    //TODO 响应数据待补充完善
    @GetMapping("/info")
    public Response<OrderInfoVo> getOrderInfo(@NotNull String orderNo) {
        OrderInfoDto orderInfo = orderService.getOrderInfo(orderNo);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        return Response.success(BusinessStates.SUCCESS, orderInfoVo);
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay")
    public Response<String> payOrder(@RequestBody @Validated PayDto pay) {
        OrderInfoDto orderInfo = orderService.getOrderInfo(pay.getOrderNo());
        if (orderInfo.getStatus() != OrderStatus.UNPAID) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED,"订单状态异常无法支付");
        }
        //扣款
        userService.debit(orderInfo.getTotalAmount());
        //更新订单状态
        orderService.updateOrderStatus(orderInfo);
        return Response.success(BusinessStates.SUCCESS);
    }
}
