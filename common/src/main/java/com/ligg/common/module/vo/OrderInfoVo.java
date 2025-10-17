/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVo {

    /**
     * 订单编号
     */
    @JsonView(Views.SimpleView.class)
    private String orderNo;

    /**
     * 用户id
     */
    @JsonView(Views.DetailView.class)
    private String userId;

    /**
     * 总金额
     */
    @JsonView(Views.SimpleView.class)
    private BigDecimal totalAmount;

    /**
     * 支付类型
     */
    @JsonView(Views.DetailView.class)
    private PayType payType;

    /**
     * 订单状态
     */
    @JsonView(Views.SimpleView.class)
    private OrderStatus status;

    /**
     * 收货地址id
     */
    @JsonView(Views.DetailView.class)
    private Long addressId;

    /**
     * 订单备注
     */
    @JsonView(Views.SimpleView.class)
    private String remark;

    /**
     * 创建时间
     */
    @JsonView(Views.SimpleView.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonView(Views.DetailView.class)
    private LocalDateTime updateTime;

    /**
     * 支付时间
     */
    @JsonView(Views.DetailView.class)
    private LocalDateTime payTime;

    @JsonProperty("paymentStatus")
    @JsonView(Views.SimpleView.class)
    public String getStatusDescription() {
        return this.status != null ? this.status.getDescription() : null;
    }
}
