package com.ligg.common.service.product;

import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.dto.ProductCommentDto;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
public interface ProductCommentService {

    /**
     * 发布商品评价
     */
    int publishComment(ProductCommentBo content);
}
