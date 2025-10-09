/**
 * @Author Ligg
 * @Time 2025/10/8
 **/
package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.common.entity.FeaturedEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {

    /**
     * 分页搜索商品
     *
     * @param page    分页参数
     * @param keyword 关键字
     * @param sort    排序(1: 默认排序 2:价格低到高 3:价格高到低 )
     * @return 商品列表
     */
    IPage<FeaturedEntity> selectCommodity(@Param("page") IPage<FeaturedEntity> page,
                                          @Param("keyword") String keyword,
                                          @Param("sort") Integer sort);
}
