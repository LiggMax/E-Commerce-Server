package com.ligg.apiadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.apiadmin.mapper.UserManagementMapper;
import com.ligg.apiadmin.pojo.vo.UserManagementVo;
import com.ligg.apiadmin.pojo.dto.UserInfoDto;
import com.ligg.apiadmin.service.UserManagementService;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.StatusEnum;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author Ligg
 * @Time 2025/10/28
 **/
@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementMapper userManagementMapper;

    private final UserMapper userMapper;

    private final RedisUtil redisUtil;

    @Override
    public PageVo<UserManagementVo> getUserListPage(Long pageNumber, Long pageSize, String search) {
        IPage<UserManagementVo> page = new Page<>(pageNumber, pageSize);
        IPage<UserManagementVo> userInfoVoIPage = userManagementMapper.selectUserList(page,search);
        PageVo<UserManagementVo> userList = new PageVo<>();
        userList.setPages(userInfoVoIPage.getPages());
        userList.setTotal(userInfoVoIPage.getTotal());
        userList.setList(userInfoVoIPage.getRecords());
        return userList;
    }

    /**
     * 修改用户状态
     */
    @Override
    public void updateUserStatus(String userId, Boolean isStatus) {
        UserEntity userInfo = userMapper.selectById(userId);
        if (userInfo == null) {
            throw new RuntimeException("用户不存在");
        }
        if (isStatus) {
            if (userInfo.getStatus() == StatusEnum.ENABLED) throw new RuntimeException("用户已启用");
            userMapper.update(new LambdaUpdateWrapper<UserEntity>()
                    .eq(UserEntity::getUserId, userId)
                    .set(UserEntity::getStatus, StatusEnum.ENABLED));
        } else {
            if (userInfo.getStatus() == StatusEnum.DISABLED) throw new RuntimeException("用户已禁用");
            userMapper.update(new LambdaUpdateWrapper<UserEntity>()
                    .eq(UserEntity::getUserId, userId)
                    .set(UserEntity::getStatus, StatusEnum.DISABLED));
        }
    }

    @Override
    public int updateUSerRoleInfo(UserInfoDto userInfo) {
        UserEntity userEntity = userMapper.selectById(userInfo.getUserId());
        if (userEntity == null) {
            throw new RuntimeException("更新的用户不存在");
        }
        LambdaUpdateWrapper<UserEntity> updateWrapper = new LambdaUpdateWrapper<UserEntity>()
                .eq(UserEntity::getUserId, userInfo.getUserId())
                .set(UserEntity::getRole, userInfo.getRole())
                .set(UserEntity::getEmail, userInfo.getEmail())
                .set(UserEntity::getNickName, userInfo.getNickName())
                .set(UserEntity::getUpdateTime, LocalDateTime.now());
        if (userInfo.getAvatar() != null && !userInfo.getAvatar().isEmpty()) {
            updateWrapper.set(UserEntity::getAvatar, userInfo.getAvatar());
        }
        int updateStatus = userMapper.update(updateWrapper);
        if (updateStatus > 0) {
            redisUtil.del(String.format("%s:%s",UserConstant.USER_INFO, userInfo.getUserId()));
        }
        return updateStatus;
    }
}
