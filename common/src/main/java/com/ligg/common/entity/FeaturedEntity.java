/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
package com.ligg.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("featured_products")
public class FeaturedEntity {

    private String id;

    private String name;

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
    private Float rating;

    private LocalDateTime createdAt;
}
