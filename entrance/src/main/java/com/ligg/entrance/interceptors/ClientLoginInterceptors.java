/**
 * @Author Ligg
 * @Time 2025/10/10
 **/
package com.ligg.entrance.interceptors;

import com.ligg.common.constants.Constant;
import com.ligg.common.constants.UserConstant;
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

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * 客户端登录拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientLoginInterceptors implements HandlerInterceptor {

    final RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constant.AUTHORIZATION);

        if (token == null) {
            throw new PermissionsException("缺少授权标头",response);
        }
        Map<String, Object> claims = JWTUtil.parseToken(token);
        String userId = (String) claims.get(UserConstant.USER_ID);
        //从Redis中获取用户信息
        String redisUserToken = (String) redisUtil.get(Constant.TOKEN + userId);
        if (redisUserToken == null || redisUserToken.isEmpty()) {
            throw new PermissionsException("授权已过期...", response);
        }
        ThreadLocalUtil.set(claims);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
