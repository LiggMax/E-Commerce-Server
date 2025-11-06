package com.ligg.apiadmin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.apiadmin.pojo.vo.OrderListVo;
import com.ligg.apiadmin.service.OrderManagementService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.Sort;
import com.ligg.common.module.entity.OrderEntity;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.utils.Response;
import com.ligg.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Ligg
 * @create_time 2025/10/22 14:28
 * @update_time 2025/11/1 14:28
 **/
@Validated
@RestController
@Tag(name = "订单管理")
@RequiredArgsConstructor
@RequestMapping("/api/admin/order")
public class AdminOrderController {

    private final OrderManagementService orderManagementService;

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "订单列表")
    public Response<PageVo<OrderListVo>> getOrderList(@Schema(description = "页码") @NotNull @Max(100) Long pageNumber,
                                                      @Schema(description = "页大小") @NotNull @Max(100) Long pageSize,
                                                      @Schema(description = "订单状态") @RequestParam(required = false) OrderStatus status,
                                                      @Schema(description = "搜索关键字") @RequestParam(required = false) String keyword,
                                                      @Schema(description = "时间排序") @RequestParam(required = false) Sort sortOrder) {
        IPage<OrderListVo> list = orderManagementService.list(pageNumber, pageSize, status, keyword, sortOrder);
        PageVo<OrderListVo> page = new PageVo<>();
        page.setTotal(list.getTotal());
        page.setPages(list.getPages());
        page.setList(list.getRecords());
        return Response.success(BusinessStates.SUCCESS, page);
    }

    @Operation(summary = "修改订单状态")
    @PatchMapping("/status/{orderNo}")
    public Response<Void> updateOrderStatus(@PathVariable String orderNo, OrderStatus status) {
        orderService.update(new LambdaUpdateWrapper<OrderEntity>()
                .eq(OrderEntity::getOrderNo, orderNo)
                .set(OrderEntity::getStatus, status)
                .set(OrderEntity::getUpdateTime, LocalDateTime.now()));
        return Response.success(BusinessStates.SUCCESS);
    }
}
