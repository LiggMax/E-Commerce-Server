package com.ligg.common.module.vo;

import lombok.Data;

/**
 * @author Ligg
 * @create_time 2025/11/9 18:41
 * @update_time 2025/11/9 18:41
 **/

@Data
public class FavoriteVo {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 名称
     */
    private String title;

    /**
     * 图片路径
     */
    private String imagePath;

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
     * 库存
     */
    private int stock;

    /**
     * 阅览量
     */
    private Integer views;
}
