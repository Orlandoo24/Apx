package com.hqzl.apx.common.interceptor;

import com.hqzl.apx.common.config.SecurityContextHolder;
import com.hqzl.apx.common.constant.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * feign 请求拦截器
 *
 * @author ruoyi
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String userId = SecurityContextHolder.get(CommonConstants.APX_USER_KEY);
        requestTemplate.header(CommonConstants.APX_USER_KEY, userId);
    }
}