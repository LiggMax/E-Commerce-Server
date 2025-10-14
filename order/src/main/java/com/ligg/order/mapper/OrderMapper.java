package com.ligg.order.mapper;

import com.ligg.common.module.dto.ProductStockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/14
 **/
@Mapper
public interface OrderMapper {

    /**
     * 获取所有商品id和库存
     */
    List<ProductStockDto> getAllProductStock();

    /**
     * 修改商品库存
     */
    @Update("update product set stock = #{productStock} where id = #{productId}")
    void updateProductStock(String productId, Integer productStock);
}
