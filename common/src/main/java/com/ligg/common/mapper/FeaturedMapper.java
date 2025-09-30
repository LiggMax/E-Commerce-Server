package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.ProductDetailEntity;
import com.ligg.common.entity.ProductImageEntity;
import com.ligg.common.vo.FeaturedDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/9/26
 **/
@Mapper
public interface FeaturedMapper extends BaseMapper<FeaturedEntity> {

    /**
     * 根据商品id查询商品详情
     * @param productId 商品id
     * @return 商品详情
     */
    @Select("select * from product_detail where product_id = #{productId}")
    ProductDetailEntity selectProductDetailById(Long productId);

    /**
     * 根据商品id查询商品图片列表
     */
    @Select("select * from product_image where product_id = #{productId}")
    List<FeaturedDetailVo.Images> selectProductImagesById(Long productId);
}
