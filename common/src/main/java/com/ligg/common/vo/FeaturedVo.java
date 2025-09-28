package com.ligg.common.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Data
public class FeaturedVo {
    private String id;

    /**
     * 名称
     */
    private String title;

    /**
     * 图片路径
     */
    private ImagesVo images;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;

    /**
     * 评价数
     */
    private Integer reviews;

    /**
     * 评分
     */
    private int rating;

    /**
     * 折扣
     */
    private Double discount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
