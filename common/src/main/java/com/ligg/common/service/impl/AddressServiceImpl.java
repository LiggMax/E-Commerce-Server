package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.Whether;
import com.ligg.common.mapper.address.AddressMapper;
import com.ligg.common.module.entity.UserAddressEntity;
import com.ligg.common.service.AddressService;
import com.ligg.common.utils.ThreadLocalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteAddress(Long addressId) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);

        UserAddressEntity userAddressEntity = addressMapper.selectById(addressId);
        if (addressMapper.deleteById(addressId) < 1) {
            throw new RuntimeException("删除收货地址失败");
        }

        if (userAddressEntity != null && userAddressEntity.getIsDefault() == Whether.YES) {
            //获取最新添加的收货地址
            UserAddressEntity userAddressOne = addressMapper.selectAddressOneByCreateTime(userId);
            if (userAddressOne != null) {
                addressMapper.updateAddressDefaultByAddressIdAndUserId(userId, Whether.YES, userAddressEntity.getId());
            }
        }
    }

    /**
     * 修改收货地址
     */
    @Override
    public int updateAddress(UserAddressEntity address) {
        return addressMapper.updateAddress(address);
    }

    /**
     * 获取收货地址
     */
    @Override
    public List<UserAddressEntity> getAddress(String userId) {
        return addressMapper.selectList(new LambdaQueryWrapper<UserAddressEntity>()
                .eq(UserAddressEntity::getUserId, userId)
                .orderByDesc(UserAddressEntity::getUpdateTime));
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

    /**
     * 修改收货地址为默认
     * 开启事务更新数据保证数据一致性，where条件确保为主键或索引否则sql语句需要手动添加"for update"避免表锁.
     * 开启事务 ——> 取消所有用户默认地址(行锁) ——> 根据addressId and userId修改为默认地址 ——> 提交事务(释放锁)
     */
    @Override
    @Transactional
    public void updateAddressDefault(Long addressId, String userId) {
        addressMapper.updateAddressDefaultByUserId(userId, Whether.NO);
        addressMapper.updateAddressDefaultByAddressIdAndUserId(userId, Whether.YES, addressId);
    }
}
