/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;

import lombok.*;

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
    @JsonView(Views.SimpleView.class)
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
     * 购买数量
     */
    @JsonView(Views.SimpleView.class)
    private Integer quantity;

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
    @JsonView(Views.DetailView.class)
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

    /**
     * 订单到期剩余时间
     */
    @JsonView(Views.SimpleView.class)
    private Long expireTime;

    /**
     * 规格信息
     */
    @JsonView(Views.SimpleView.class)
    private List<SpecValue> specValues;

    /**
     * 收货地址详情
     */
    @JsonView(Views.SimpleView.class)
    private Address address;

    /**
     * 订单商品信息
     */
    @JsonView(Views.SimpleView.class)
    private Product product;

    @JsonProperty("paymentStatus")
    @JsonView(Views.SimpleView.class)
    public String getStatusDescription() {
        return this.status != null ? this.status.getDescription() : null;
    }

    @Getter
    @Setter
    public static class SpecValue {
        @JsonView(Views.SimpleView.class)
        private int id;
        @JsonView(Views.SimpleView.class)
        private String value;
    }

    @Getter
    @Setter
    public static class Address {

        /**
         * 收货地址id
         */
        @JsonView(Views.SimpleView.class)
        private Long id;

        /**
         * 收货人名称
         */
        @JsonView(Views.SimpleView.class)
        private String receiverName;

        /**
         * 收货人手机号码
         */
        @JsonView(Views.SimpleView.class)
        private String receiverPhone;

        /**
         * 省
         */
        @JsonView(Views.SimpleView.class)
        private String province;

        /**
         * 市
         */
        @JsonView(Views.SimpleView.class)
        private String city;

        /**
         * 区/县
         */
        @JsonView(Views.SimpleView.class)
        private String district;

        /**
         * 详情地址
         */
        @JsonView(Views.SimpleView.class)
        private String detailAddress;
    }

    @Getter
    @Setter
    public static class Product {
        /**
         * 商品id
         */
        @JsonView(Views.SimpleView.class)
        private String id;

        /**
         * 商品标题
         */
        @JsonView(Views.SimpleView.class)
        private String title;

        /**
         * 商品封面
         */
        @JsonView(Views.SimpleView.class)
        private String image;
    }
}
