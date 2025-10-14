package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.SpecValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Ligg
 * @Time 2025/10/9
 **/
@Mapper
public interface SpecValueMapper extends BaseMapper<SpecValueEntity> {

    /**
     * 根据规格id查询规格值内容
     */
    @Select("select id,spec_id,value,sort,create_time,update_time,price from spec_value where spec_id = #{specId}")
    SpecValueEntity getSpecValuePriceBySpecId(Integer specId);
}
