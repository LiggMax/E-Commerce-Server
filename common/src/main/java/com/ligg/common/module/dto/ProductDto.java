/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.common.module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProductDto {

    @Schema(description = "商品id")
    private String id;

    /**
     * 标题
     */
    @NotNull
    @Pattern(regexp = "^.{1,20}$", message = "标题长度不能超过20个字符")
    @Schema(description = "标题")
    private String title;

    /**
     * 原价
     */
    @Schema(description = "原价")
    private Double originalPrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 现价
     */
    @Schema(description = "现价")
    private Double currentPrice;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
}
