package com.ligg.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Data
public class FeaturedVo {
    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图片路径
     */
    @Schema(description = "图片链接s")
    private ImagesVo images;

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

    /**
     * 评价数
     */
    @Schema(description = "评价数")
    private Integer reviews;

    /**
     * 评分
     */
    @Schema(description = "评分")
    private int rating;

    /**
     * 折扣
     */
    @Schema(description = "折扣")
    private Double discount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
