package com.ligg.common.mapper.address;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.enums.Default;
import com.ligg.common.module.entity.UserAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author Ligg
 * @Time 2025/10/16
 **/
@Mapper
public interface AddressMapper extends BaseMapper<UserAddressEntity> {

    /**
     * 修改地址
     */
    int updateAddress(UserAddressEntity address);

    /**
     * 根据userId修改地址为默认地址
     */
    @Update("update user_address set is_default = #{isDefault} where user_id = #{userId}")
    void updateAddressDefaultByUserId(String userId, Default isDefault);

    /**
     * 根据addressId和userId修改默认地址
     */
    @Update("update user_address set is_default = #{isDefault} where user_id = #{userId} and id = #{addressId}")
    void updateAddressDefaultByAddressIdAndUserId(String userId, Default isDefault, Long addressId);

    /**
     * 根据userId查询创建时间最新的一条收货地址
     */
    @Select("select * from user_address where user_id = #{userId} order by create_time desc limit 1")
    UserAddressEntity selectAddressOneByCreateTime(String userId);
}
