package com.ligg.entrance.config;

import com.ligg.entrance.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Ligg
 * @Time 2025/9/22
 *
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        registry.addInterceptor(loginInterceptors)
                //拦截路径
                .addPathPatterns("/api/admin/**")
                //放行路径
                .excludePathPatterns("/api/admin/account/**");
    }
}
