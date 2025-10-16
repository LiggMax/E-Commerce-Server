package com.ligg.common.module.vo;

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
public class AddressVo {

    /**
     * 收货地址id
     */
    private Long id;

    /**
     * 收货人名称
     */
    private String receiverName;

    /**
     * 收货人手机号码
     */
    private String receiverPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 是否默认地址
     */
    private Boolean isDefault;

    /**
     * 详情地址
     */
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
