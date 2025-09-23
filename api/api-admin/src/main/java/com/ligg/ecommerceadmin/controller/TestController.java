package com.ligg.ecommerceadmin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Ligg
 * @Time 2025/9/22
 **/
@RestController
@RequestMapping("/api/admin")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
