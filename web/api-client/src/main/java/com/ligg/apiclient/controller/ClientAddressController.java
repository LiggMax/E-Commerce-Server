/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.Default;
import com.ligg.common.module.dto.AddressDto;
import com.ligg.common.module.entity.UserAddressEntity;
import com.ligg.common.module.vo.AddressVo;
import com.ligg.common.service.address.AddressService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 收货地址
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client/user/address")
public class ClientAddressController {

    private final AddressService addressService;

    /**
     * 获取收货地址
     */
    @GetMapping
    public Response<List<AddressVo>> getAddress() {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        return Response.success(BusinessStates.SUCCESS, addressService.getAddress(userId).stream().map(entity -> {
            AddressVo addressVo = new AddressVo();
            BeanUtils.copyProperties(entity, addressVo);
            addressVo.setIsDefault(entity.getIsDefault() == Default.YES);
            return addressVo;
        }).toList());
    }

    /**
     * 添加收货地址
     */
    @PostMapping
    public Response<String> addAddress(@Validated @RequestBody AddressDto address) {
        UserAddressEntity userAddressEntity = new UserAddressEntity();
        BeanUtils.copyProperties(address, userAddressEntity);
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        userAddressEntity.setUserId(userId);
        userAddressEntity.setCreateTime(LocalDateTime.now());
        userAddressEntity.setUpdateTime(LocalDateTime.now());
        return addressService.addAddress(userAddressEntity) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }


    /**
     * 修改收货地址
     */
    @PutMapping
    public Response<String> updateAddress(@Validated @RequestBody AddressDto address) {
        UserAddressEntity entity = new UserAddressEntity();
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserAddressEntity userAddress = addressService.getAddressByIdAndUserId(address.getId(), userId);
        if (!userId.equals(userAddress.getUserId())) {
            return Response.error(BusinessStates.FORBIDDEN, "没有权限修改收货地址");
        }

        BeanUtils.copyProperties(address, entity);
        entity.setUserId(userId);
        entity.setUpdateTime(LocalDateTime.now());
        return addressService.updateAddress(entity) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除收货地址
     */
    @DeleteMapping("/{id}")
    public Response<String> deleteAddress(@PathVariable Long id) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserAddressEntity userAddress = addressService.getAddressByIdAndUserId(id, userId);
        if (!userId.equals(userAddress.getUserId())) {
            return Response.error(BusinessStates.FORBIDDEN, "没有权限删除收货地址");
        }

        return addressService.deleteAddress(id) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 设置默认收货地址
     */
    @PatchMapping("/{id}")
    public Response<String> setDefaultAddress(@PathVariable Long id) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserAddressEntity userAddress = addressService.getAddressByIdAndUserId(id, userId);
        if (!userId.equals(userAddress.getUserId())) {
            return Response.error(BusinessStates.FORBIDDEN, "没有权限设置默认收货地址");
        }

        return Response.success(BusinessStates.SUCCESS);
    }
}
