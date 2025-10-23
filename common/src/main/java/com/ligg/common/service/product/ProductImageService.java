package com.ligg.common.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.module.entity.ProductImageEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
public interface ProductImageService extends IService<ProductImageEntity> {

    /**
     * 获取图片列表
     * @param productId 商品id
     * @return List<FeaturedImageVo>
     */
    List<ProductImageEntity> getList(@NotNull String productId);

    /**
     * 根据商品id获取图片列表
     * @param featuredId 商品id
     * @return 图片列表
     */
    List<ProductImageEntity> getImagesByFeaturedId(String featuredId);
}
