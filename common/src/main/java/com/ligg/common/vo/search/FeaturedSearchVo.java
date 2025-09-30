/**
 * @Author Ligg
 * @Time 2025/9/30
 **/
package com.ligg.common.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 查询结果Vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeaturedSearchVo {
    /**
     * 商品ID
     */
    private String id;

    /**
     * 商品名称
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
    private Integer rating;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

    /**
     * 商品描述
     */
    private String description;
}
