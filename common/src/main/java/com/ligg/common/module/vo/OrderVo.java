package com.ligg.common.module.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Ligg
 * @Time 2025/10/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long id;

    /**
     * 商品封面
     */
    private String cover;

    /**
     * 订单编号
     */
    private String orderNo;


    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 订单状态
     */
    private OrderStatus status;

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
     * 支付时间
     */
    private LocalDateTime payTime;
}
