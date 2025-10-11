/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单明细
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称(冗余)
     */
    private String productName;

    /**
     * 下单商品价格(冗余)
     */
    private Double productPrice;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 小计金额(价格x数量)
     */
    private Double subtotal;
}
