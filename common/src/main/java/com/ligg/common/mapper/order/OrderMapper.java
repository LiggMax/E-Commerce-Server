package com.ligg.common.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.dto.ProductStockDto;
import com.ligg.common.module.entity.OrderEntity;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.module.vo.OrderVo;
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
     * 分批获取商品id和库存
     *
     * @param offset 偏移量
     * @param limit  限制数量
     */
    @Select("select id as productId,stock from product limit #{offset},#{limit}")
    List<ProductStockDto> getProductStockByBatch(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 修改商品库存
     */
    @Update("update product set stock = #{productStock} where id = #{productId}")
    void updateProductStock(String productId, Integer productStock);

    /**
     * 查询订单基本信息
     */
    @Select("select * from orders where order_no = #{oredrNo} ")
    OrderEntity selectOrderBaseInfo(String orderNo);

    /**
     * 根据订单号查询订单信息
     */
    OrderInfoVo selectOrderByOrderNo(String orderNo);

    /**
     * 根据订单号获取规格内容list
     */
    List<OrderInfoVo.SpecValue> selectOrderItemSpecListByOrderNo(String orderNo);

    /**
     * 根据用户id查询订单列表
     */
    IPage<OrderVo> selectOrderListByUserId(@Param("page") IPage<OrderVo> page, String userId, OrderStatus status, String keyword);
}
