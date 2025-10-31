package com.ligg.apiadmin.service;

import com.ligg.apiadmin.pojo.UserManagementVo;
import com.ligg.common.module.vo.PageVo;
import jakarta.validation.constraints.NotNull;

/**
 * @Author Ligg
 * @Time 2025/10/28
 **/
public interface UserManagementService {
    /**
     * 获取用户列表
     */
    PageVo<UserManagementVo> getUserListPage(@NotNull Long pageNumber, @NotNull Long pageSize);

    /**
     * 修改用户状态
     */
    void updateUserStatus(@NotNull String userId, @NotNull Boolean status);
}
