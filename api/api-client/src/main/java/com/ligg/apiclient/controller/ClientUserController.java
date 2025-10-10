package com.ligg.apiclient.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.entity.UserEntity;
import com.ligg.common.service.UserService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import com.ligg.common.vo.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
@RestController
@RequestMapping("/api/client/user")
@RequiredArgsConstructor
public class ClientUserController {

    final UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/user_info")
    public Response<UserInfoVo> getUserInfo() {
        Map<String, Object> userObject = ThreadLocalUtil.get();
        String userId = (String) userObject.get(Constant.USER_ID);
        UserEntity userInfo = userService.getUserInfoById(userId);
        return null;
    }
}
