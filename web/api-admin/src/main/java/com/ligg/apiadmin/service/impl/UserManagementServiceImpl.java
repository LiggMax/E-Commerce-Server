package com.ligg.apiadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ligg.apiadmin.mapper.UserManagementMapper;
import com.ligg.apiadmin.pojo.UserManagementVo;
import com.ligg.apiadmin.service.UserManagementService;
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
}
