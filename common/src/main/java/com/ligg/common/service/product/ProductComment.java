package com.ligg.common.service.product;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
public interface ProductComment {

    /**
     * 发布评论
     */
    int publishComment(Long productId, String content);
}
