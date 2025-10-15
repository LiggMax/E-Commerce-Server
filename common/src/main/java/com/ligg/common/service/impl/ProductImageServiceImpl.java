package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.module.entity.ProductImageEntity;
import com.ligg.common.mapper.ProductImageMapper;
import com.ligg.common.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImageEntity>
        implements ProductImageService {

    private final ProductImageMapper productImageMapper;


    /**
     * 获取图片列表
     *
     * @param productId 商品id
     * @return List<FeaturedImageVo>
     */
    @Override
    public List<ProductImageEntity> getList(String productId) {
        return productImageMapper.selectList(new LambdaQueryWrapper<ProductImageEntity>()
                .eq(ProductImageEntity::getProductId, productId));
    }

    /**
     * 根据商品id获取图片列表
     *
     * @param productId 商品id
     * @return 图片列表
     */
    @Override
    public List<ProductImageEntity> getImagesByFeaturedId(String productId) {
        return productImageMapper.selectImageListById(productId);
    }
}
