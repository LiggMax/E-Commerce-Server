/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.entrance.interceptors;

import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
import com.ligg.common.enums.BusinessStates;
import com.ligg.common.enums.UserRole;
import com.ligg.common.exception.PermissionsException;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;


/**
 * 登录拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminLoginInterceptors implements HandlerInterceptor {

    private final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(Constant.AUTHORIZATION);
        if (token == null) {
            throw new PermissionsException("未获取授权...", BusinessStates.UNAUTHORIZED.getCode());
        }
        Map<String, Object> claims = JWTUtil.parseToken(token);
        String userRole = (String) claims.get(UserConstant.USER_ROLE);
        String userId = (String) claims.get(UserConstant.USER_ID);
        //从Redis中获取用户信息
        String redisUserToken = (String) redisUtil.get(Constant.TOKEN + userId);
        if (redisUserToken == null || redisUserToken.isEmpty() || !userRole.equals(UserRole.ADMIN.name())) {
            throw new PermissionsException("没用权限...",response);
        }
        ThreadLocalUtil.set(claims);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalUtil.remove();
    }
}
