/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 总金额
     */
    private Double totalAmount;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;
}
