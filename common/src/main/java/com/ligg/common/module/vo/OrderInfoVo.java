/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVo {

    /**
     * 订单编号
     */
    @JsonView(Views.Public.class)
    private String orderNo;

    /**
     * 用户id
     */
    @JsonView(Views.Public.class)
    private String userId;

    /**
     * 总金额
     */
    @JsonView(Views.Public.class)
    private BigDecimal totalAmount;

    /**
     * 支付类型
     */
    @JsonView(Views.Public.class)
    private PayType payType;

    /**
     * 订单状态
     */
    @JsonView(Views.Public.class)
    private OrderStatus status;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 订单备注
     */
    @JsonView(Views.Public.class)
    private String remark;

    /**
     * 创建时间
     */
    @JsonView(Views.Public.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    @JsonProperty("paymentStatus")
    public String getStatusDescription() {
        return this.status != null ? this.status.getDescription() : null;
    }
}
