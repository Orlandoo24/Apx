package com.hqzl.apx.blockchain.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("googleAuth")
@RestController
public class TwoFactorController {

    @GetMapping("get2faSecretKey")
    public void get2faSecretKey() {

    }


    @GetMapping("/secretKey")
    public void check2faSecretKey(String secretKey){

    }
}
