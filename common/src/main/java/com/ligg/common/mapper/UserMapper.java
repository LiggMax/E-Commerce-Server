package com.ligg.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.FavoriteVo;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

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
     * 更新用户信息
     * @param userEntity 用户信息
     * @return 1
     */
    int updateUserInfo(UserEntity userEntity);

    /**
     * 根据邮箱更新用户信息
     */
    int updateUserInfoByEmail(UserEntity userEntity);

    /**
     * 充值
     * @param amount 充值金额
     * @param userId 用户id
     */
    @Update("update user set account_balance = account_balance + #{amount} where user_id = #{userId}")
    int recharge(BigDecimal amount, String userId);

    /**
     * 查询用户收藏商品
     */
    List<FavoriteVo> selectUserFavorite(String userId);
}
