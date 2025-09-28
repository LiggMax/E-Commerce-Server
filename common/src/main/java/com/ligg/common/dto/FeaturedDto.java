package com.ligg.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
@Data
public class FeaturedDto {

    @Schema(description = "商品id")
    private String id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private Double originalPrice;

    /**
     * 现价
     */
    @Schema(description = "现价")
    private Double currentPrice;
}
