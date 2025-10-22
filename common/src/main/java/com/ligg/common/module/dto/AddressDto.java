package com.ligg.common.module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    /**
     * 收货地址id
     */
    private Long id;

    /**
     * 收货人名称
     */
    @NotNull
    @Schema(description = "收货人名称")
    private String receiverName;

    /**
     * 收货人手机号码
     */
    @NotNull
    private String receiverPhone;

    /**
     * 省
     */
    @NotNull
    private String province;

    /**
     * 市
     */
    @NotNull
    private String city;

    /**
     * 区/县
     */
    @NotNull
    private String district;

    /**
     * 是否默认地址
     */
    private Boolean isDefault;

    /**
     * 详情地址
     */
    @NotNull
    @Pattern(regexp = "^.{5,120}$")
    private String detailAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
