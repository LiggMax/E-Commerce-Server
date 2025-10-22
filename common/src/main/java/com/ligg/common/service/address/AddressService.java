package com.ligg.common.service.address;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.module.entity.UserAddressEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
public interface AddressService extends IService<UserAddressEntity> {

    /**
     * 添加收货地址
     */
    int addAddress(UserAddressEntity address);

    /**
     * 删除收货地址
     */
    void deleteAddress(Long addressId);

    /**
     * 修改收货地址
     */
    int updateAddress(UserAddressEntity address);

    /**
     * 获取收货地址
     */
    List<UserAddressEntity> getAddress(String userId);

    /**
     * 根据地址id和用户id获取收货地址
     */
    UserAddressEntity getAddressByIdAndUserId(Long addressId, String userId);

    /**
     * 修改地址为默认地址
     */
    void updateAddressDefault(@NotNull Long addressId, String userId);
}
