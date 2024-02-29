package com.hqzl.apx.common.config;


import com.hqzl.apx.common.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 一定不要忽略此注解
public class WebMvcInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserTokenInterceptor userTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册对象将拦截器添加进框架中
        registry.addInterceptor(userTokenInterceptor)
                // 配置拦截规则
                // 拦截所有的 url
                .addPathPatterns("/**")
                // 排除用户注册 url
                .excludePathPatterns("/thirdPartyAuth/**")
                .order(10);
    }
}