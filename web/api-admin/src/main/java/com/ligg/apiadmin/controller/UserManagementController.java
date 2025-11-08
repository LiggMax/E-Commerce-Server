package com.ligg.apiadmin.controller;

import com.ligg.apiadmin.pojo.vo.UserManagementVo;
import com.ligg.apiadmin.pojo.dto.UserInfoDto;
import com.ligg.apiadmin.service.UserManagementService;
import com.ligg.common.constants.Constant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.PageVo;
import com.ligg.common.service.FileService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.BCryptUtil;
import com.ligg.common.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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

    private final FileService fileService;

    private final UserService userService;

    private final UserManagementService userManagementService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @GetMapping
    public Response<PageVo<UserManagementVo>> Userlist(@NotNull Long pageNumber,
                                                       @NotNull Long pageSize,
                                                       @RequestParam(required = false) String search) {
        PageVo<UserManagementVo> userListPage = userManagementService.getUserListPage(pageNumber, pageSize, search);
        return Response.success(BusinessStates.SUCCESS, userListPage);
    }

    @Operation(summary = "添加用户信息")
    @PostMapping
    public Response<String> addUserInfo(@Validated UserInfoDto userInfo,
                                        MultipartFile avatarFile) {
        if (userService.lambdaQuery().eq(UserEntity::getEmail, userInfo.getEmail()).exists()) {
            return Response.error(BusinessStates.METHOD_NOT_ALLOWED, "该邮箱已经被使用,请更换邮箱");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userInfo, userEntity);
        if (avatarFile != null && !avatarFile.isEmpty()) {
            if (avatarFile.getSize() > Constant.FILE_SIZE) {
                return Response.error(BusinessStates.VALIDATION_FAILED, "文件大小不可大于2mb");
            }
            String avatarPath = fileService.uploadImage(avatarFile, Constant.AVATAR_FILE_PATH);
            userEntity.setAvatar(avatarPath);
        }
        userEntity.setPassword(BCryptUtil.encrypt(userEntity.getPassword()));
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setUpdateTime(LocalDateTime.now());
        userManagementService.addUserInfo(userEntity);
        return Response.success(BusinessStates.SUCCESS);
    }


    @PutMapping
    @Operation(summary = "更新用户信息")
    public Response<String> updateUserInfo(@Validated UserInfoDto userInfo,
                                           MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty()) {
            if (avatarFile.getSize() > Constant.FILE_SIZE) {
                return Response.error(BusinessStates.VALIDATION_FAILED, "文件大小不可大于2mb");
            }
            String avatarPath = fileService.uploadImage(avatarFile, Constant.AVATAR_FILE_PATH);
            userInfo.setAvatar(avatarPath);
        }
        return userManagementService.updateUSerRoleInfo(userInfo) > 0
                ? Response.success(BusinessStates.SUCCESS)
                : Response.error(BusinessStates.INTERNAL_SERVER_ERROR);
    }

    /**
     * 更新用户主状态
     */
    @Operation(summary = "更新用户主状态")
    @PatchMapping(value = "/status", headers = "Content-Type=multipart/form-data")
    public Response<String> updateUserStatus(@NotNull String userId,
                                             @NotNull Boolean isStatus) {
        userManagementService.updateUserStatus(userId, isStatus);
        return Response.success(BusinessStates.SUCCESS);
    }
}
