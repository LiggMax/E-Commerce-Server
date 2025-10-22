/**
 * @Author Ligg
 * @Time 2025/10/11
 **/
package com.ligg.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.module.dto.OrderDto;
import com.ligg.common.module.dto.PayDto;
import com.ligg.common.module.entity.OrderEntity;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.module.vo.OrderVo;
import com.ligg.common.module.vo.PageVo;
import jakarta.validation.constraints.NotNull;

public interface OrderService extends IService<OrderEntity> {

    /**
     * 创建订单
     */
    String createOrder(OrderDto orderDto);

    /**
     * 根据订单编号获取订单基本信息
     *
     * @param orderNo 订单号
     * @return 订单基本信息
     */
    OrderEntity getOrderByOderNo(String orderNo);

    /**
     * 获取订单详情信息
     */
    OrderInfoVo getOrderInfo(String orderNo);

    /**
     * 获取订单过期时间
     */
    Long getOrderExpireTime(String orderNo);

    /**
     * 支付订单
     */
    void payOrder(@NotNull PayDto pay);

    /**
     * 获取用户订单列表
     */
    PageVo<OrderVo> getUserOrderList(Long pageNum, Long pageSize, OrderStatus status, String keyword);

    /**
     * 获取订单列表
     */
    PageVo<OrderVo> getOrderList(Long pageNum, Long pageSize);

    /**
     * 修改订单状态为取消
     */
    void cancelOrder(Long orderId);
}
