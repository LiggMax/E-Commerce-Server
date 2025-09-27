package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.vo.FeaturedVo;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
public interface FeaturedService extends IService<FeaturedEntity> {

    /**
     * 保存推荐商品
     * @param featuredEntity 推荐商品信息
     */
    void saveFeatured(FeaturedEntity featuredEntity);
}
