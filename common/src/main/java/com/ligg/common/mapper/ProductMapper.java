package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.common.module.entity.ProductEntity;
import com.ligg.common.module.entity.ProductDetailEntity;
import com.ligg.common.module.vo.search.FeaturedSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
@Mapper
public interface ProductMapper extends BaseMapper<ProductEntity> {

    /**
     * 根据商品id查询商品详情
     *
     * @param productId 商品id
     * @return 商品详情
     */
    @Select("select id,product_id,description from product_detail where product_id = #{productId}")
    ProductDetailEntity selectProductDetailById(String productId);

    IPage<FeaturedSearchVo> selectProductDetailPage(IPage<FeaturedSearchVo> page);

    /**
     * 扣减商品库存
     */
    @Update("update product set stock = stock - #{quantity} where id = #{productId}")
    int updateStockDeduct(Long productId, Integer quantity);

    /**
     * 增加商品库存
     */
    @Update("update product set stock = stock + #{quantity} where id = #{productId}")
    int updateStockAdd(Long productId, Integer quantity);
}
