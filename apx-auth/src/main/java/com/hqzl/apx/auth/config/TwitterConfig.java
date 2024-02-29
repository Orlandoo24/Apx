package com.hqzl.apx.auth.config;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TwitterConfig {

    private final static String CONSUMER_KEY = "oJzQLrCgkEPc7LKKKAOsZIIHk";
    private final static String CONSUMER_SECRET = "yekgGFOLtmRqFti5YsdUuqxZai4Ii7Po0Rp4fiu77EMxgrwJcd";
    private final static String REQUEST_TOKEN_URL = "https://api.twitter.com/oauth/request_token";
    private final static String VERIFIER_TOKEN_URL = "https://api.twitter.com/oauth/access_token";
    private final static String GET_USERINFO_IN_TWITTER = "https://api.twitter.com/2/users/me";

    /**
     * @param callBackUrl 回调地址，前端传入， 在twitter官网上配置的有多个回调地址，具体跳转到哪一个，前端控制
     * @return
     */
    public static String requestToken(String callBackUrl) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpPost request = new HttpPost(REQUEST_TOKEN_URL);

        // 服务器上不需要
        setProxy(request);

        // OAuth
        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        HttpParameters parameters = new HttpParameters();
        parameters.put(OAuth.OAUTH_CALLBACK, callBackUrl);
        consumer.setAdditionalParameters(parameters);
        consumer.sign(request);
        return httpClientExecute(request);
    }


    public static String verifierToken(String oauthToken, String oauthVerifier) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        HttpPost request = new HttpPost(VERIFIER_TOKEN_URL);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        // 创建参数列表
        List<NameValuePair> bodyParam = new ArrayList<>();
        bodyParam.add(new BasicNameValuePair("oauth_verifier", oauthVerifier));
        // 将参数转换为UrlEncodedFormEntity
        StringEntity entity = new UrlEncodedFormEntity(bodyParam, StandardCharsets.UTF_8);
        // 设置HttpPost的实体
        request.setEntity(entity);



        // 服务器上不需要
        setProxy(request);



        // 创建CommonsHttpOAuthConsumer对象，设置OAuth1验证参数
        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        HttpParameters parameters = new HttpParameters();
        parameters.put(OAuth.OAUTH_TOKEN, oauthToken);
        consumer.setAdditionalParameters(parameters);
        consumer.sign(request);

        return httpClientExecute(request);
    }


    public static String getUserInfo(String token, String secret) throws URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
        // 创建API请求，例如获取用户的时间线
        CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(token, secret);

        HttpGet request = new HttpGet(new URIBuilder(GET_USERINFO_IN_TWITTER).build());
        request.setHeader("Content-Type", "application/json");


        // 要删除
        setProxy(request);


        consumer.sign(request);
        return httpClientExecute(request);
    }

    public static void setProxy(HttpRequestBase request) {
//      设置系统代理 ，部署到服务器删除
        HttpHost proxy = new HttpHost("127.0.0.1", 10900); // 代理服务器的主机和端口
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        request.setConfig(config);
    }

    public static String httpClientExecute(HttpRequestBase request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        return responseBody;
    }
}