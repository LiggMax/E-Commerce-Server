package com.ligg.entrance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.ligg")
@MapperScan("com.ligg.**.mapper")
public class EntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntranceApplication.class, args);
    }

}
