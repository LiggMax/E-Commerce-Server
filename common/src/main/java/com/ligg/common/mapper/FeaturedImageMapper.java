package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.entity.FeaturedImageEntity;
import com.ligg.common.vo.FeaturedImageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/7
 **/
@Mapper
public interface FeaturedImageMapper extends BaseMapper<FeaturedImageEntity> {

    /**
     * 根据商品id获取图片列表
     */
    @Select("select id,sort,image_path from featured_image where featured_id = #{featuredId}")
    List<FeaturedImageEntity> selectImageListById(String featuredId);
}
