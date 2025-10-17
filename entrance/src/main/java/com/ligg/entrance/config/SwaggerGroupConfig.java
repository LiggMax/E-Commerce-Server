/**
 * @Author Ligg
 * @Time 2025/10/17
 **/
package com.ligg.entrance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 */
@Configuration
public class SwaggerGroupConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API")
                        .description("Ecommerce后端系统")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ligg")
                                .url("https://github.com/LiggMax/E-commerce-api.git"))
                        .license(new License().name("Apache 2.0").url("https://github.com/LiggMax/E-Commerce-Server/blob/main/LICENSE")));
    }

    @Bean
    public GroupedOpenApi clientApi() {
        return GroupedOpenApi.builder()
                .group("客户端接口")
                .packagesToScan("com.ligg.apiclient")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("管理端接口")
                .packagesToScan("com.ligg.apiadmin")
                .build();
    }

    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("通用接口")
                .packagesToScan("com.ligg.apicommon")
                .build();
    }

}
