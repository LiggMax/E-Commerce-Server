package com.ligg.apiadmin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ligg.apiadmin.pojo.vo.OrderListVo;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.Sort;

import java.util.List;

/**
 * @author Ligg
 * @create_time 2025/11/1 14:28
 * @update_time 2025/11/1 14:28
 **/
public interface OrderManagementMapper {
    IPage<OrderListVo> list(IPage<OrderListVo> page, OrderStatus status, String keyword, Sort sortOrder);

    /**
     * 获取订单商品规格信息
     */
    List<OrderListVo.Product.Spec> selectOrderSpecValueByOrderNo(String orderNo);
}
