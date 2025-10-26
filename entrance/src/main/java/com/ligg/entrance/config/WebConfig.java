/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
package com.ligg.entrance.config;

import com.ligg.entrance.interceptors.ClientLoginInterceptors;
import com.ligg.entrance.interceptors.AdminLoginInterceptors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminLoginInterceptors adminLoginInterceptors;
    private final ClientLoginInterceptors clientLoginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //管理端拦截器
        registry.addInterceptor(adminLoginInterceptors)
                //拦截路径
                .addPathPatterns("/api/admin/**")
                //放行路径
                .excludePathPatterns("/api/admin/auth/**","/api/admin/system");
        //客户端拦截器
        registry.addInterceptor(clientLoginInterceptors)
                //拦截路径
                .addPathPatterns("/api/client/user/**",
                        "/api/client/order/**")
                //放行路径
                .excludePathPatterns();
    }
}
