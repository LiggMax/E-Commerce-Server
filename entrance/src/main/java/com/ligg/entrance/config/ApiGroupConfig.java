//package com.ligg.entrance.config;
//
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ApiGroupConfig {
//
//    @Bean
//    public GroupedOpenApi fileApi() {
//        return GroupedOpenApi.builder()
//                .group("admin接口")
//                .packagesToScan("com.example.knife4jdemo.controller")
//                .pathsToMatch("/api/file/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi userApi() {
//        return GroupedOpenApi.builder()
//                .group("client接口")
//                .packagesToScan("com.example.knife4jdemo.controller")
//                .pathsToMatch("/api/user/**")
//                .build();
//    }
//}
