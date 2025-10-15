package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.ProductImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
@Mapper
public interface ProductImageMapper extends BaseMapper<ProductImageEntity> {

    /**
     * 根据商品id获取图片列表
     */
    @Select("select id,sort,image_path from product_image where product_id = #{productId}")
    List<ProductImageEntity> selectImageListById(String productId);
}
