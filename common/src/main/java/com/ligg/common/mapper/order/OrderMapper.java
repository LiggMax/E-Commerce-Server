package com.ligg.common.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.dto.ProductStockDto;
import com.ligg.common.module.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    /**
     * 获取所有商品id和库存
     */
    @Select("select id as productId,stock as stock from product")
    List<ProductStockDto> getAllProductStock();

    /**
     * 分批获取商品id和库存
     * @param offset 偏移量
     * @param limit 限制数量
     */
    @Select("select id as productId,stock from product limit #{offset},#{limit}")
    List<ProductStockDto> getProductStockByBatch(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改商品库存
     */
    @Update("update product set stock = #{productStock} where id = #{productId}")
    void updateProductStock(String productId, Integer productStock);
}
