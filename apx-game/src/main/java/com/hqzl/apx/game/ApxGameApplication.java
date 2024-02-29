package com.hqzl.apx.game;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.hqzl.apx")
@MapperScan(basePackages = "com.hqzl.apx.game.dao")
public class ApxGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApxGameApplication.class, args);
    }
}
