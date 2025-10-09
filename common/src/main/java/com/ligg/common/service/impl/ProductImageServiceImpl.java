package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.ProductImageEntity;
import com.ligg.common.mapper.ProductImageMapper;
import com.ligg.common.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImageEntity>
        implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;


    /**
     * 获取图片列表
     *
     * @param featuredId 商品id
     * @return List<FeaturedImageVo>
     */
    @Override
    public List<ProductImageEntity> getList(String featuredId) {
        return productImageMapper.selectList(new LambdaQueryWrapper<ProductImageEntity>()
                .eq(ProductImageEntity::getProductId, featuredId));
    }

    /**
     * 根据商品id获取图片列表
     *
     * @param featuredId 商品id
     * @return 图片列表
     */
    @Override
    public List<ProductImageEntity> getImagesByFeaturedId(String featuredId) {
        return productImageMapper.selectImageListById(featuredId);
    }
}
