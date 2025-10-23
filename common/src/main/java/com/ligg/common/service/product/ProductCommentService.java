package com.ligg.common.service.product;

import com.ligg.common.module.bo.ProductCommentBo;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.ProductCommentVo;
import jakarta.validation.constraints.NotNull;

/**
 * @author Ligg
 * @Time 2025/10/23
 **/
public interface ProductCommentService {

    /**
     * 发布商品评价
     */
    int publishComment(ProductCommentBo content);

    /**
     * 获取商品评价
     */
    PageVo<ProductCommentVo> getCommentByProductId(@NotNull String productId,Long pageNumber, Long pageSize);
}
