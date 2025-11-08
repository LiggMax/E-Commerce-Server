package com.ligg.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.UserInfoVo;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
public interface UserService extends IService<UserEntity> {
    /**
     * 注册账号
     *
     * @param account
     * @param password
     */
    void register(@NotNull String account, @NotNull String password);

    /**
     * 根据账号获取用户信息
     */
    UserEntity getUserInfoByEmail(String email);

    /**
     * 获取用户信息
     */
    UserInfoVo getUserInfo();

    /**
     * 账户扣款
     */
    void debit(@NotNull BigDecimal amount);

    /**
     * 更新用户头像
     */
    void updateUserAvatar(MultipartFile avatarFile);

    /**
     * 更新用户信息
     */
    int updateUserInfo(UserEntity userEntity);

    /**
     * 充值
     **/
    int recharge(BigDecimal amount, String userId);

    /**
     * 商品收藏
     */
    int productFavorite(Long productId, boolean isFavorite);


}
