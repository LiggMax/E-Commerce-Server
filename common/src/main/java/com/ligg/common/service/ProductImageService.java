package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.ProductImageEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
public interface ProductImageService extends IService<ProductImageEntity> {

    /**
     * 获取图片列表
     * @param featuredId 商品id
     * @return List<FeaturedImageVo>
     */
    List<ProductImageEntity> getList(@NotNull String featuredId);

    /**
     * 根据商品id获取图片列表
     * @param featuredId 商品id
     * @return 图片列表
     */
    List<ProductImageEntity> getImagesByFeaturedId(String featuredId);
}
