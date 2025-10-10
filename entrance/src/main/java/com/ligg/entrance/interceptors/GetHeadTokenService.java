package com.ligg.entrance.interceptors;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @Author LiGG
 * @Time 2025/10/10
 */
public class GetHeadTokenService {

    public static String getToken(HttpServletRequest  request, String tokenName){
        return request.getHeader(tokenName);
    }
}
