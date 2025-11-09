package com.ligg.common.service.impl.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.constants.ProductConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductDetailEntity;
import com.ligg.common.mapper.product.ProductMapper;
import com.ligg.common.service.product.ProductService;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.module.vo.search.FeaturedSearchVo;
import com.ligg.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Ligg
 * @Time 2025/9/27
 **/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

    private final ProductMapper productMapper;

    private final RedisUtil redisUtil;

    @Override
    public void saveFeatured(ProductEntity product) {
        int insert = productMapper.insert(product);
        if (insert > 0) {
            //商品库存缓存没有过期时间
            redisUtil.set(ProductConstant.STOCK_KEY_PREFIX + product.getId(), product.getStock());
        }
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
     *
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

    @Override
    @Transactional
    public int updateStockDeduct(Long productId, Integer quantity) {
        return productMapper.updateStockDeduct(productId, quantity);
    }

    @Override
    @Transactional
    public int updateStockAdd(Long productId, Integer quantity) {
        return productMapper.updateStockAdd(productId, quantity);
    }


}
