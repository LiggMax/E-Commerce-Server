package com.ligg.apiadmin.pojo.vo;

import com.fasterxml.jackson.annotation.JsonView;
import com.ligg.common.enums.OrderStatus;
import com.ligg.common.enums.PayType;
import com.ligg.common.module.vo.OrderInfoVo;
import com.ligg.common.module.vo.Views;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ligg
 * @time 2025/11/1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVo {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户信息
     */
    private UserInfo user;

    /**
     * 商品信息
     */
    private Product product;

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
     * 购买数量
     */
    private Integer quantity;

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

    /**
     * 收货地址详情
     */
    private OrderInfoVo.Address address;


    ///商品信息
    @Getter
    @Setter
    public static class Product{
        private String productId;
        private String title;
        private String image;
        private List<Spec> product_spec;

        @Getter
        @Setter
        public static class Spec{
            private Long id;
            private String value;
        }
    }


    ///用户信息
    @Getter
    @Setter
    public static class UserInfo{
        private String userId;
        private String nickName;
    }
}
