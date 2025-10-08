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
public class ProductImageServiceImpl extends ServiceImpl<FeaturedImageMapper, FeaturedImageEntity>
        implements FeaturedImageService {

    @Autowired
    private FeaturedImageMapper featuredImageMapper;

    @Override
    public List<FeaturedImageVo> getList(String featuredId) {
        List<FeaturedImageEntity> featuredImageEntities = featuredImageMapper.selectList(new LambdaQueryWrapper<FeaturedImageEntity>()
                .eq(FeaturedImageEntity::getFeaturedId, featuredId));
        if (featuredImageEntities != null && !featuredImageEntities.isEmpty()) {
            return featuredImageEntities.stream().map(
                            entity -> new FeaturedImageVo(entity.getId(), entity.getFeaturedId(), entity.getSort(), entity.getImagePath()))
                    .toList();
        }
        return List.of();
    }
}
