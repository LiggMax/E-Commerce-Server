/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.exception.OrderException;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.service.FileService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.module.vo.UserInfoVo;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RedisUtil redisUtil;

    private final UserMapper userMapper;

    private final FileService fileService;

    @Value("${file.image.base-path}")
    private String IMAGE_PATH;

    /**
     * 注册账号
     *
     * @param account  账号
     * @param password 密码
     */
    @Override
    public void register(String account, String password) {

        String encryptPassword = BCryptUtil.encrypt(password);
        String nickName = "user_" + UUID.randomUUID().toString().substring(0, 6);
        LocalDateTime now = LocalDateTime.now();

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(encryptPassword);
        userEntity.setAccount(account);
        userEntity.setNickName(nickName);
        userEntity.setCreateTime(now);
        userEntity.setAvatar("/");
        userMapper.insert(userEntity);
    }

    /**
     * 根据账号获取用户信息
     */
    @Override
    public UserEntity getUserInfoByAccount(String account) {
        return userMapper.selectOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getAccount, account));
    }

    /**
     * 根据用户id获取用户信息
     */
    @Override
    public UserInfoVo getUserInfoById(String userId) {
        UserEntity redisUserInfo = getRedisUserInfo(userId);
        UserInfoVo userInfoVo = new UserInfoVo();

        if (redisUserInfo == null) {
            UserEntity userEntity = userMapper.selectById(userId);
            if (userEntity != null) {
                redisUtil.set(UserConstant.USER_INFO + ":" + userId, userEntity, 1);
                BeanUtils.copyProperties(userEntity, userInfoVo);
            }
            return userInfoVo;
        }

        BeanUtils.copyProperties(redisUserInfo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 扣减用户余额
     */
    @Override
    @Transactional
    public void debit(@NotNull BigDecimal amount) {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        UserEntity userEntity = userMapper.selectById(userId);
        BigDecimal accountBalance = userEntity.getAccountBalance();
        if (accountBalance.compareTo(amount) < 0) {
            throw new OrderException("余额不足");
        }
        if (!(userMapper.debitBalance(userId, amount) > 0)) {
            throw new RuntimeException("扣减用户余额失败");
        }
    }

    /**
     * 更新用户头像
     */
    @Override
    public void updateUserAvatar(MultipartFile avatarFile) {
        Map<String, Object> UserInfo = ThreadLocalUtil.get();
        String userId = (String) UserInfo.get(UserConstant.USER_ID);

        UserEntity userInfo = userMapper.selectById(userId);
        String avatar = userInfo.getAvatar();
        //如果头像不 != null || "" 则删除旧头像
        if (avatar != null && !avatar.equals("/")) {
            String avatarPath = IMAGE_PATH + avatar.replace(Constant.IMAGE_RELATIVE_PATH, "");
            fileService.deleteFileAsync(avatarPath);
        }
        String imagePath = fileService.uploadImage(avatarFile, Constant.AVATAR_FILE_PATH);
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(imagePath);
        userEntity.setUserId(userId);
        userMapper.updateUserInfo(userEntity);
    }

    /**
     * 更新用户信息
     */
    @Override
    public int updateUserInfo(UserEntity userEntity) {
        int number = userMapper.updateUserInfo(userEntity);
        if (number > 0) {
            String userKey = UserConstant.USER_INFO + ":" + userEntity.getUserId();
            redisUtil.del(userKey);
        }
        return number;
    }

    private UserEntity getRedisUserInfo(String userId) {
        return (UserEntity) redisUtil.get(UserConstant.USER_INFO + ":" + userId);
    }
}
