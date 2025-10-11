package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductDetailEntity;
import com.ligg.common.mapper.ProductMapper;
import com.ligg.common.service.ProductService;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.search.FeaturedSearchVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public void saveFeatured(ProductEntity featuredEntity) {
        productMapper.insert(featuredEntity);
    }

    /**
     * 获取精选商品分页列表
     *
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @return 精选商品分页列表
     */
    @Override
    public PageVo<ProductEntity> getFeaturedPageList(Long pageNumber, Long pageSize) {
        Page<ProductEntity> page = new Page<>(pageNumber, pageSize);
        productMapper.selectPage(page, null);
        PageVo<ProductEntity> pageVo = new PageVo<>();
        pageVo.setPages(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setList(page.getRecords());
        return pageVo;
    }

    /**
     * 获取精选商品详情分页列表
     * 自定义搜索条件
     * @param pageNumber 页码
     * @param pageSize   每页数量
     * @return 精选商品详情分页列表
     */
    @Override
    public PageVo<FeaturedSearchVo> getProductDetailPagelist(Long pageNumber, Long pageSize) {
        //创建分页对象
        Page<FeaturedSearchVo> page = new Page<>(pageNumber, pageSize);

        // 分页查询
        IPage<FeaturedSearchVo> result = productMapper.selectProductDetailPage(page);
        //封装PageVo
        PageVo<FeaturedSearchVo> pageVo = new PageVo<>();
        pageVo.setPages(result.getPages());
        pageVo.setTotal(result.getTotal());
        pageVo.setList(result.getRecords());
        return pageVo;
    }

    /**
     * 根据商品id查询商品详情
     */
    @Override
    public ProductDetailEntity getFeaturedDetailById(String productId) {
        return productMapper.selectProductDetailById(productId);
    }

    /**
     * 根据id更新商品图片路径
     */
    @Override
    public void updateImagePathById(String id, String imagePath) {

    }
}
