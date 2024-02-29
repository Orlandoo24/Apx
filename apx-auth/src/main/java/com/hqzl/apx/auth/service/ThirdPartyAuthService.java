package com.hqzl.apx.auth.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface ThirdPartyAuthService {

    /**
     *
     * @param callBackUrl  给推特用，用于完成第二步重定向的网页【重定向网页有多个】
     * @return "oauth_token": "903qxQAAAAABr_b_AAABjXMPYWo"    放在推特重定向网址后面 有了这个，用户输入完账号和密码，就会重定向到callBackUrl 地址
     */
    Map<String,String> requestToken(String callBackUrl);


    /**
     * 返回的参数，有用户的token 和 secret ，用于请求 2/user/me这个接口，拿用户信息
     * @param oauthToken    这两个参数是推特重定向后在URL中 拿到的参数，用于验证以上步骤没有错误
     * @param oauthVerifier
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    Map<String, Object> getApxToken(String oauthToken, String oauthVerifier);
}
