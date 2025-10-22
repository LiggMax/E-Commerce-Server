package com.ligg.apiadmin.controller;

import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.vo.OrderVo;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.utils.Response;
import com.ligg.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ligg
 * @Time 2025/10/22
 **/
@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    /**
     * 获取订单列表
     */
    @GetMapping
    @Operation(summary = "获取订单列表")
    public Response<PageVo<OrderVo>> getOrderList(Long pageNum, Long pageSize) {
        return Response.success(BusinessStates.SUCCESS, orderService.getOrderList(pageNum, pageSize));
    }
}
