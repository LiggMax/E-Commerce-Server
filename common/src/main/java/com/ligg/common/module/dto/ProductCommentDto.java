package com.ligg.common.module.dto;

import com.ligg.common.enums.Whether;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Data
public class ProductCommentDto {
    /**
     * 商品id
     */
    @NotNull
    private Long productId;

    /**
     * 评价内容
     */
    @NotNull
    private String content;

    /**
     * 评分
     */
    @NotNull
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
