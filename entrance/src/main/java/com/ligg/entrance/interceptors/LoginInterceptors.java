package com.ligg.entrance.interceptors;

import com.ligg.utils.JWTUtil;
import com.ligg.utils.RedisUtil;
import com.ligg.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @Author Ligg
 * @Time 2025/9/22
 * <p>
 * 登录拦截器
 **/
@Slf4j
@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String Token = request.getHeader("Authorization");

        try {
            Map<String, Object> claims = jwtUtil.parseToken(Token);
            String userId = (String) claims.get("userId");
            //从Redis中获取用户信息
            String redisUserToken = (String) redisUtil.get("Token:" + userId);
            if (redisUserToken == null) {
                throw new RuntimeException();
            }
            Map<String, Object> userInfo = jwtUtil.parseToken(redisUserToken);
            ThreadLocalUtil.set(userInfo);
            return true;
        } catch (Exception e) {
            log.error("令牌验证失败:", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
