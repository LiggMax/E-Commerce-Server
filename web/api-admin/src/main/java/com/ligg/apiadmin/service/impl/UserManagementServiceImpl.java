package com.ligg.apiadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.apiadmin.mapper.UserManagementMapper;
import com.ligg.apiadmin.pojo.UserManagementVo;
import com.ligg.apiadmin.service.UserManagementService;
import com.ligg.common.enums.StatusEnum;
import com.ligg.common.mapper.UserMapper;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.PageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author Ligg
 * @Time 2025/10/28
 **/
@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements UserManagementService {

    private final UserManagementMapper userManagementMapper;

    private final UserMapper userMapper;

    @Override
    public PageVo<UserManagementVo> getUserListPage(Long pageNumber, Long pageSize) {
        IPage<UserManagementVo> page = new Page<>(pageNumber, pageSize);
        IPage<UserManagementVo> userInfoVoIPage = userManagementMapper.selectUserList(page);
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
}
