package com.hqzl.apx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.hqzl.apx")
public class ApxAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApxAuthApplication.class, args);
    }
}