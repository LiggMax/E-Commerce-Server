package com.ligg.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/

@Component
public class JWTUtil {

    @Value("${jwt.key}")
    private String KEY;

    /**
     * 生成token
     *
     * @return token
     */
    public String createToken(Map<String, Object> claims,Long expire) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * 解析token
     *
     * @return token解析获取到的数据
     */
    public Map<String, Object> parseToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(KEY))
                    .build()
                    .verify(token)
                    .getClaim("claims")
                    .asMap();

        } catch (TokenExpiredException e) {
            //处理令牌过期的情况,响应状态码401
            throw new TokenExpiredException("令牌已过期", e.getExpiredOn());
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            throw new RuntimeException("Invalid JWT token", exception);
        }
    }

}
