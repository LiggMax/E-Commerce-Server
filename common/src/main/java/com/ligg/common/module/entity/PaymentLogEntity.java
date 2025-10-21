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

/**
 * 支付记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLogEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 第三方支付流水号
     */
    private String transactionId;

    /**
     * 支付金额
     */
    private Double totalAmount;

    /**
     * 支付状态
     */
    private OrderStatus payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;
}
