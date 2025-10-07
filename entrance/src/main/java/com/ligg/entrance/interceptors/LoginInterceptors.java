/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.entrance.interceptors;

import com.ligg.common.constants.Constant;
import com.ligg.common.utils.JWTUtil;
import com.ligg.common.utils.RedisUtil;
import com.ligg.common.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;


/**
 * 登录拦截器
 */
@Slf4j
@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String Token = request.getHeader(Constant.AUTHORIZATION);

        try {
            if (Token == null) {
                throw new RuntimeException("缺少授权标头");
            }
            Map<String, Object> claims = jwtUtil.parseToken(Token);
            String userId = (String) claims.get(Constant.USER_ID);
            //从Redis中获取用户信息
            String redisUserToken = (String) redisUtil.get(Constant.TOKEN + ":" + userId);
            if (redisUserToken == null || redisUserToken.isEmpty()) {
                throw new RuntimeException("未获得授权...");
            }
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            log.error("令牌验证失败:", e);
            response.setStatus(SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadLocalUtil.remove();
    }
}
