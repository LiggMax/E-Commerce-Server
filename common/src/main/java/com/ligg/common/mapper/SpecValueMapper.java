package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.ProductSpecValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
@Mapper
public interface SpecValueMapper extends BaseMapper<ProductSpecValueEntity> {

    /**
     * 根据规格id查询规格值内容
     */
    @Select("select id,spec_id,value,sort,create_time,update_time,price from product_spec_value where spec_id = #{specId}")
    ProductSpecValueEntity getSpecValuePriceBySpecId(Integer specId);
}
