package com.hqzl.apx.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.hqzl.apx")
public class ApxBlockchainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApxBlockchainApplication.class, args);
    }
}