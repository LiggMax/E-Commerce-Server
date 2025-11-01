package com.ligg.apiadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.apiadmin.pojo.vo.OrderListVo;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.vo.PageVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

/**
 * @Author Ligg
 * @Time 2025/11/1
 **/
public interface OrderManagementService {
    IPage<OrderListVo> list(@NotNull @Max(100) Long pageNumber,
                            @NotNull @Max(100) Long pageSize,
                            OrderStatus status,
                            String keyword);
}
