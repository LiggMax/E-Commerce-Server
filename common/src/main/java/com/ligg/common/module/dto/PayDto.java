/**
 * @Author Ligg
 * @Time 2025/10/17
 **/
package com.ligg.common.module.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ligg.common.enums.PayType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDto {

    /**
     * 订单号
     */
    @NotNull
    private String orderNo;

    /**
     * 支付密码
     */
    //TODO 暂时还不支持
    @JsonIgnore
    private String payPassword;

    /**
     * 支付类型
     */
    private PayType payType;
}
