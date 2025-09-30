package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.ProductDetailEntity;
import com.ligg.common.entity.ProductImageEntity;
import com.ligg.common.vo.FeaturedDetailVo;
import com.ligg.common.vo.PageVo;

import java.util.List;

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
     * 根据id获取精选商品详情
     *
     * @param productId 商品id
     * @return 精选商品详情
     */
    ProductDetailEntity getProductDetailById(Long productId);

    /**
     * 根据商品id查询商品图片列表
     */
    List<FeaturedDetailVo.Images> selectProductImagesById(Long productId);
}
