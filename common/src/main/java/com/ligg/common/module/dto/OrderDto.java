/**
 * @Author LiGG
 * @Time 2025/10/11
 */
package com.ligg.common.module.dto;

import com.ligg.common.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 支付类型
     */
    private PayType payType;

    /**
     * 收货地址id
     */
    private Long addressId;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 规格
     */
    private List<SpecDto> spec;

    /**
     * 规格
     */
    public static class SpecDto {
        private Integer id;
        private SpecValueDto specValue;
    }

    /**
     * 规格值
     */
    public static class SpecValueDto {
        private Integer id;
    }
}
