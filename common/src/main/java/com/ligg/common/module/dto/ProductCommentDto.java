package com.ligg.common.module.dto;

import com.ligg.common.enums.Whether;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
public class ProductCommentDto {
    /**
     * 商品id
     */
    private Long productId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评分
     */
    private Integer rating;

    /**
     * 评论类型
     */
    private Integer type;


    /**
     * 是匿名评论
     */
    private Whether isAnonymous;
}
