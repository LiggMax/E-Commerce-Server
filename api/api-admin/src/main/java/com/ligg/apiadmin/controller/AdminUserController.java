/**
 * @Author LiGG
 * @Time 2025/10/6
 */
package com.ligg.apiadmin.controller;

import com.ligg.common.constants.Constant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.service.TokenService;
import com.ligg.common.utils.Response;
import com.ligg.common.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * admin用户接口
 */
@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    @Autowired
    private TokenService tokenService;

    /**
     * 登出
     */
    @PostMapping("/remove_token")
    public Response<String> logout() {
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        String userId = (String) userInfo.get(Constant.USER_ID);
        if (userId == null || userId.isEmpty()) {
            return Response.error(BusinessStates.SUCCESS);
        }
        tokenService.deleteRedisToken(userId);
        return Response.success(BusinessStates.SUCCESS);
    }

}
