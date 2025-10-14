/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.apiclient.controller;

import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.common.module.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/api/client/user")
@RequiredArgsConstructor
public class ClientUserController {

    private final UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Response<UserInfoVo> getUserInfo() {
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(UserConstant.USER_ID);
        UserInfoVo userInfo = userService.getUserInfoById(userId);
        return Response.success(BusinessStates.SUCCESS, userInfo);
    }
}
