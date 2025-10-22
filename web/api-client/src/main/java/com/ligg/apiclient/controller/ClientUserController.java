/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.dto.UserDto;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.common.module.vo.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

/**
 * 用户接口
 */
@Tag(name = "用户接口", description = "管理账户信息")
@RestController
@RequestMapping("/api/client/user")
@RequiredArgsConstructor
public class ClientUserController {

    private final UserService userService;

    /**
     * 获取用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Response<UserInfoVo> getUserInfo() {
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(UserConstant.USER_ID);
        UserInfoVo userInfo = userService.getUserInfoById(userId);
        return Response.success(BusinessStates.SUCCESS, userInfo);
    }

    @PutMapping
    public Response<String> updateUserInfo(@Validated UserDto userDto,
                                           MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty() && Objects.requireNonNull(avatarFile.getContentType()).startsWith("image")) {
            if (avatarFile.getSize() > 1024 * 1024 * 2) {
                return Response.error(BusinessStates.VALIDATION_FAILED, "图片大小不能超过2M");
            }
            userService.updateUserAvatar(avatarFile);
        }
        Map<String, Object> UserInfo = ThreadLocalUtil.get();
        String userId = (String) UserInfo.get(UserConstant.USER_ID);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setUserId(userId);
        return userService.updateUserInfo(userEntity) < 1
                ? Response.error(BusinessStates.INTERNAL_SERVER_ERROR)
                : Response.success(BusinessStates.SUCCESS);
    }
}
