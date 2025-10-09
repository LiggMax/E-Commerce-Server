package com.ligg.common.service;

import com.ligg.common.entity.ProductEntity;
import com.ligg.common.vo.PageVo;
import jakarta.validation.constraints.NotNull;

/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
public interface SearchService {
    PageVo<ProductEntity> searchCommodityPageList(@NotNull String keyword, Long pageNumber, long pageSize, Integer sort);
}
