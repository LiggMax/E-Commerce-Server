package com.ligg.common.service.impl.address;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.mapper.address.AddressMapper;
import com.ligg.common.module.entity.UserAddressEntity;
import com.ligg.common.service.address.AddressService;
import com.ligg.common.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
@Service
@RequiredArgsConstructor
public class AddressServiceImpl extends ServiceImpl<AddressMapper, UserAddressEntity> implements AddressService {

    private final AddressMapper addressMapper;

    /**
     * 添加收货地址
     */
    @Override
    public int addAddress(UserAddressEntity address) {
        return addressMapper.insert(address);
    }

    /**
     * 删除收货地址
     */
    @Override
    public int deleteAddress(Long id) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserAddressEntity userAddressEntity = addressMapper.selectById(id);
        if (!userId.equals(userAddressEntity.getUserId())) {
            throw new RuntimeException("没有权限修删除收货地址");
        }
        return addressMapper.deleteById(id);
    }

    /**
     * 修改收货地址
     */
    @Override
    public int updateAddress(UserAddressEntity address) {
        return addressMapper.update(new LambdaUpdateWrapper<UserAddressEntity>()
                .eq(UserAddressEntity::getUserId, address.getUserId())
                .eq(UserAddressEntity::getId, address.getId())
                .set(UserAddressEntity::getIsDefault, address.getIsDefault())
                .set(UserAddressEntity::getReceiverName, address.getReceiverName())
                .set(UserAddressEntity::getReceiverPhone, address.getReceiverPhone())
                .set(UserAddressEntity::getProvince, address.getProvince())
                .set(UserAddressEntity::getCity, address.getCity())
                .set(UserAddressEntity::getDistrict, address.getDistrict())
                .set(UserAddressEntity::getDetailAddress, address.getDetailAddress())
                .set(UserAddressEntity::getUpdateTime, address.getUpdateTime()));
    }

    /**
     * 获取收货地址
     */
    @Override
    public List<UserAddressEntity> getAddress(String userId) {
        return addressMapper.selectList(new LambdaQueryWrapper<UserAddressEntity>()
                .eq(UserAddressEntity::getUserId, userId));
    }

    /**
     * 根据地址id和用户id获取收货地址
     */
    @Override
    public UserAddressEntity getAddressByIdAndUserId(Long addressId, String userId) {
        return addressMapper.selectOne(new LambdaQueryWrapper<UserAddressEntity>()
                .eq(UserAddressEntity::getId, addressId)
                .eq(UserAddressEntity::getUserId, userId));
    }
}
