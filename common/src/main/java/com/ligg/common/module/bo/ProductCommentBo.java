package com.ligg.common.module.bo;

import com.ligg.common.enums.Whether;
import lombok.*;

import java.util.List;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommentBo {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 图片(json数组)
     */
    private List<String> images;

    /**
     * 评论类型
     */
    private Integer type;


    /**
     * 是匿名评论
     */
    private Whether isAnonymous;
}
