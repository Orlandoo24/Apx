package com.hqzl.apx.common.interceptor;

import com.hqzl.apx.common.config.SecurityContextHolder;
import com.hqzl.apx.common.constant.CommonConstants;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Order(10)
@Component
public class UserTokenInterceptor implements HandlerInterceptor {

    // 将用户id 存入本地线程
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String value = request.getHeader(CommonConstants.APX_USER_KEY);
        SecurityContextHolder.set(CommonConstants.APX_USER_KEY, value);
        return true;
    }


    // 线程运行结束后，删除
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }
}