/**
 * @Author LiGG
 * @Time 2025/10/6
 */
package com.ligg.apiadmin.controller;

import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.module.entity.UserEntity;
import com.ligg.common.module.vo.UserInfoVo;
import com.ligg.common.service.TokenService;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "admin用户接口")
@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final TokenService tokenService;

    private final UserService userService;

    /**
     * 登出
     */
    @PostMapping("/remove_token")
    public Response<String> logout() {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(UserConstant.USER_ID);
        if (userId == null || userId.isEmpty()) {
            return Response.error(BusinessStates.SUCCESS);
        }
        tokenService.deleteRedisToken(userId);
        return Response.success(BusinessStates.SUCCESS);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Response<UserInfoVo> getUserInfo() {
        UserInfoVo userInfo = userService.getUserInfo();
        return Response.success(BusinessStates.SUCCESS, userInfo);
    }
}
