package com.ligg.common.module.dto;

import com.ligg.common.enums.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Ligg
 * @Time 2025/10/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private BigDecimal amount;
    private PayType payType;
}
