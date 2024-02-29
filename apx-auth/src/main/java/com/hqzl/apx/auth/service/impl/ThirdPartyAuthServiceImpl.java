package com.hqzl.apx.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqzl.apx.auth.config.TwitterConfig;
import com.hqzl.apx.auth.service.ThirdPartyAuthService;
import com.hqzl.apx.auth.utils.CustomURLEncodeUtils;
import com.hqzl.apx.common.constant.BlockchainConstants;
import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.exception.ServiceException;
import com.hqzl.apx.common.utils.JwtUtil;
import com.hqzl.apx.mbg.mapper.UserMapper;
import com.hqzl.apx.mbg.model.User;
import lombok.extern.slf4j.Slf4j;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ThirdPartyAuthServiceImpl implements ThirdPartyAuthService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> requestToken(String callBackUrl) {
        try {
            return CustomURLEncodeUtils.requestTokenToMap(TwitterConfig.requestToken(callBackUrl));
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException e) {
            throw new ServiceException("OAuth 认证失败");
        }
    }


    @Override
    public Map<String, Object> getApxToken(String oauthToken, String oauthVerifier) {

        log.info("oauthToken 的值是{}, oauthVerifier的值是{}" , oauthToken, oauthVerifier);
        Map<String, String> tokenSecret = null;
        try {
            tokenSecret = CustomURLEncodeUtils.requestTokenToMap(TwitterConfig.verifierToken(oauthToken, oauthVerifier));
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException e) {
            e.printStackTrace();
            throw new ServiceException("token 验证失败");
        }

        String userInfo;
        try {
            log.info("oauth_token 的值是{}, oauth_token_secret 的值是{}" + tokenSecret.get("oauth_token"), tokenSecret.get("oauth_token_secret"));
            userInfo = TwitterConfig.getUserInfo(tokenSecret.get("oauth_token"), tokenSecret.get("oauth_token_secret"));
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new ServiceException("从推特中获取用户信息失败");
        }

        Map<String, Object> result = checkUser(userInfo);
        return result;
    }

    private String createToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("expirationTime", System.currentTimeMillis() + CommonConstants.DAY_TIME_STAMP);
        String token = JwtUtil.generateToken(map);
        return token;
    }

    /**
     * 检查用户是否已经注册，没有注册则user表中插入一条用户数据
     *
     * @param userInfo
     * @return
     */
    private Map<String, Object> checkUser(String userInfo) {

        JSONObject jsonObject = JSONUtil.parseObj(userInfo);
        JSONObject data = jsonObject.getJSONObject("data");
        if (ObjectUtil.isEmpty(data)) {
            throw new ServiceException("Authenticating with Unknown is forbidden for this endpoint.  Supported authentication types are [OAuth 1.0a User Context, OAuth 2.0 User Context]");
        }

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getTpId, data.get("id").toString());

        User user = userMapper.selectOne(lambdaQueryWrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("first", false);
        if (ObjectUtil.isEmpty(user)) {
            user = new User();
            user.setTpId(data.get("id").toString());
            user.setTpName(data.get("name").toString());
            user.setTpUsername(data.get("username").toString());
            user.setMyInvitationCode(CommonConstants.APX_INVITATION_CODE + RandomUtil.randomString(8));
            userMapper.insert(user);

            // 链上业务
            HashMap<String, Object> param = new HashMap();
            param.put("userId", user.getId()+"");

            String resp = HttpUtil.post(CommonConstants.HTTP + BlockchainConstants.ON_CHAIN_SERVER_IP + "/newAccount",JSONUtil.toJsonStr(param));
            Map<String, String> map = JSONUtil.toBean(resp, Map.class);

            String address = map.get("address");
            String encryptedPrivateKey = map.get("encryptedPrivateKey");
            if (StrUtil.isBlank(address) || StrUtil.isBlank(encryptedPrivateKey)) {
                throw new ServiceException("链上服务器请求失败");
            }
            user.setAddress(address);
            user.setEncryptedPrivateKey(encryptedPrivateKey);
            userMapper.updateById(user);
            result.put("first", true);
        }
        String token = createToken(user);
        result.put("token", token);
        return result;
    }
}
