package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.FeaturedImageEntity;
import com.ligg.common.vo.FeaturedImageVo;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
public interface FeaturedImageService extends IService<FeaturedImageEntity> {

    /**
     * 获取图片列表
     * @param featuredId 商品id
     * @return List<FeaturedImageVo>
     */
    List<FeaturedImageVo> getList(@NotNull String featuredId);

    /**
     * 根据商品id获取图片列表
     * @param featuredId 商品id
     * @return 图片列表
     */
    List<FeaturedImageEntity> getImagesByFeaturedId(String featuredId);
}
