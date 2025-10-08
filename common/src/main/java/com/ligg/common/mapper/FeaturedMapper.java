package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.common.entity.FeaturedEntity;
import com.ligg.common.entity.FeaturedDetailEntity;
import com.ligg.common.vo.search.FeaturedSearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from featured_detail where featured_id = #{productId}")
    FeaturedDetailEntity selectProductDetailById(String productId);

    IPage<FeaturedSearchVo> selectProductDetailPage(IPage<FeaturedSearchVo> page);

//    @Select("select * from product_image where product_id = #{productId}")
//    List<FeaturedDetailVo.Images> selectProductImagesById(Long productId);
}
