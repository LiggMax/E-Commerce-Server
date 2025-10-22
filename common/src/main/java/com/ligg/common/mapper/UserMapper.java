package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param amount 减去金额
     * @return 扣减结果
     */
    @Update("update user set account_balance = account_balance - #{amount} where user_id = #{userId}")
    int debitBalance(String userId, @NotNull BigDecimal amount);

    /**
     * 更新用户头像
     * @param userId 用户id
     * @param imagePath 头像路径
     */
    @Update("update user set avatar = #{imagePath} where user_id = #{userId}")
    void updateAvatar(String userId, String imagePath);

    /**
     * 更新用户信息
     * @param userEntity 用户信息
     * @return 1
     */
    int updateUserInfo(UserEntity userEntity);

    /**
     * 充值
     * @param amount 充值金额
     * @param userId 用户id
     */
    @Update("update user set account_balance = account_balance + #{amount} where user_id = #{userId}")
    int recharge(BigDecimal amount, String userId);
}
