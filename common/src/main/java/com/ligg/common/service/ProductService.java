package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.entity.ProductEntity;
import com.ligg.common.entity.ProductDetailEntity;
import com.ligg.common.vo.PageVo;
import com.ligg.common.vo.search.FeaturedSearchVo;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
public interface ProductService extends IService<ProductEntity> {

    /**
     * 保存精选商品
     * @param featuredEntity 精选商品信息
     */
    void saveFeatured(ProductEntity featuredEntity);

    /**
     * 获取分页精选商品列表
     * @return 精选商品列表
     */
    PageVo<FeaturedSearchVo> getProductDetailPagelist(Long pageNumber, Long pageSize);

    /**
     * 根据id获取精选商品详情
     *
     * @param productId 商品id
     * @return 精选商品详情
     */
    ProductDetailEntity getFeaturedDetailById(String productId);

    /**
     * 根据id更新精选商品图片路径
     */
    void updateImagePathById(String id, String imagePath);

    /**
     * 获取精选商品分页列表
     * @param pageNumber 页码
     * @param pageSize 每页数量
     * @return 精选商品分页列表
     */
    PageVo<ProductEntity> getFeaturedPageList(Long pageNumber, Long pageSize);

    /**
     * 根据商品id查询商品图片列表
     */
//    List<FeaturedDetailVo.Images> selectProductImagesById(Long productId);
}
