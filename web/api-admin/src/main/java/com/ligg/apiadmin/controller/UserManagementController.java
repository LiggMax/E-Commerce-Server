package com.ligg.apiadmin.controller;

import com.ligg.apiadmin.pojo.UserManagementVo;
import com.ligg.apiadmin.service.UserManagementService;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Ligg
 * @Time 2025/10/28
 **/
@Validated
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users/management")
public class UserManagementController {

    private final UserManagementService userManagementService;

    /**
     * 获取用户列表
     */
    @GetMapping
    public Response<PageVo<UserManagementVo>> Userlist(@NotNull Long pageNumber,
                                                       @NotNull Long pageSize) {
        PageVo<UserManagementVo> userListPage = userManagementService.getUserListPage(pageNumber, pageSize);
        return Response.success(BusinessStates.SUCCESS,userListPage);
    }

    /**
     * 更新用户主状态
     */
    @PatchMapping("/status")
    public Response<String> updateUserStatus(@NotNull String userId,
                                              @NotNull Boolean isStatus) {
        userManagementService.updateUserStatus(userId, isStatus);
        return Response.success(BusinessStates.SUCCESS);
    }
}
