package com.ligg.apiadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.apiadmin.pojo.vo.OrderListVo;
import com.ligg.apiadmin.service.OrderManagementService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.vo.OrderVo;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @Operation(summary = "订单列表")
    public Response<PageVo<OrderListVo>> getOrderList(@Schema(description = "页码") @NotNull @Max(100) Long pageNumber,
                                                      @Schema(description = "页大小") @NotNull @Max(100) Long pageSize,
                                                      @Schema(description = "订单状态") @RequestParam(required = false) OrderStatus status,
                                                      @Schema(description = "搜索关键字") @RequestParam(required = false) String keyword) {
        IPage<OrderListVo> list = orderManagementService.list(pageNumber, pageSize, status, keyword);
        PageVo<OrderListVo> page = new PageVo<>();
        page.setTotal(list.getTotal());
        page.setPages(list.getPages());
        page.setList(list.getRecords());
        return Response.success(BusinessStates.SUCCESS, page);
    }
}
