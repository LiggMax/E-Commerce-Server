package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.vo.PageVo;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
public interface FeaturedService extends IService<FeaturedEntity> {

    /**
     * 保存精选商品
     * @param featuredEntity 精选商品信息
     */
    void saveFeatured(FeaturedEntity featuredEntity);

    /**
     * 获取分页精选商品列表
     * @return 精选商品列表
     */
    PageVo<FeaturedEntity> Pagelist(Long pageNumber, Long pageSize);

    /**
     * 根据id更新精选商品信息
     * @param featured 精选商品信息
     */
    void updateFeaturedById(FeaturedEntity featured);
}
