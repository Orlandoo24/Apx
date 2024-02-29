package com.hqzl.apx.auth.controller;


import com.hqzl.apx.auth.service.ThirdPartyAuthService;
import com.hqzl.apx.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("thirdPartyAuth")
public class ThirdPartyAuthController {

    @Autowired
    private ThirdPartyAuthService ThirdPartyAuthService;

    @GetMapping("twitter/requestToken")
    public Result requestToken(String callBackUrl) {
        return Result.success(ThirdPartyAuthService.requestToken(callBackUrl));
    }

    @GetMapping("twitter/getApxToken")
    public Result getApxToken(String oauthToken , String oauthVerifier) {
        return Result.success(ThirdPartyAuthService.getApxToken(oauthToken,oauthVerifier));
    }

}
