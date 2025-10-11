package com.ligg.common.module.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/25
 *
 * <p>
 * 分页
 **/
@Data
public class PageVo<T> {
    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Long pages;

    /**
     * 当前页数据
     */
    @Schema(description = "当前页数据")
    private List<T> list;
}
