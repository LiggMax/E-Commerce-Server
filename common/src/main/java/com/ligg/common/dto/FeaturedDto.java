package com.ligg.common.dto;

import lombok.Data;

/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
@Data
public class FeaturedDto {

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 原价
     */
    private Double originalPrice;

    /**
     * 现价
     */
    private Double currentPrice;
}
