package com.hqzl.apx.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// EnableEurekaServer 开启 Eureka 服务器
@EnableEurekaServer
@SpringBootApplication
public class ApxEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApxEurekaApplication.class,args);
    }
}
