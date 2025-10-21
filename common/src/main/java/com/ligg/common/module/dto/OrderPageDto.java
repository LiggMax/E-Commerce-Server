package com.ligg.common.module.dto;

import com.ligg.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ligg
 * @Time 2025/10/21
 **/
@Data
public class OrderPageDto {

    /**
     * 页码
     */
    @NotNull
    private Long pageNum;

    /**
     * 页大小
     */
    @NotNull
    private Long pageSize;

    /**
     * 订单状态
     */
    private OrderStatus status;
}
