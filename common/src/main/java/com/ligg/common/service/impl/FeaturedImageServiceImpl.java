package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.entity.FeaturedImageEntity;
import com.ligg.common.mapper.FeaturedImageMapper;
import com.ligg.common.service.FeaturedImageService;
import com.ligg.common.vo.FeaturedImageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
@Service
public class FeaturedImageServiceImpl extends ServiceImpl<FeaturedImageMapper, FeaturedImageEntity>
        implements FeaturedImageService {

    @Autowired
    private FeaturedImageMapper featuredImageMapper;


    /**
     * 获取图片列表
     *
     * @param featuredId 商品id
     * @return List<FeaturedImageVo>
     */
    @Override
    public List<FeaturedImageEntity> getList(String featuredId) {
        return featuredImageMapper.selectList(new LambdaQueryWrapper<FeaturedImageEntity>()
                .eq(FeaturedImageEntity::getFeaturedId, featuredId));
    }

    /**
     * 根据商品id获取图片列表
     *
     * @param featuredId 商品id
     * @return 图片列表
     */
    @Override
    public List<FeaturedImageEntity> getImagesByFeaturedId(String featuredId) {
        return featuredImageMapper.selectImageListById(featuredId);
    }
}
