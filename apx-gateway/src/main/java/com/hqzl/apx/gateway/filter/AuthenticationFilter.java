package com.hqzl.apx.gateway.filter;

import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.constant.TokenConstants;
import com.hqzl.apx.common.utils.JwtUtil;
import com.hqzl.apx.gateway.utils.ServletUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 用户验证过滤器
 * @author
 */
@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    List<String> whiteList = Arrays.asList(
            "/apx-auth/thirdPartyAuth/twitter/requestToken",
            "/apx-auth/thirdPartyAuth/twitter/getApxToken");


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获得请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        //对白名单中的地址放行
        for (String str : whiteList) {
            if (request.getURI().getPath().contains(str)) {
                log.info("白名单，放行{}", request.getURI().getPath());
                return chain.filter(exchange);
            }
        }
        //获得请求头中Authorization token信息
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        Claims claims ;
        try {
            //解析token
            claims = JwtUtil.parseToken(token);
        } catch (Exception ex) {
            return unauthorizedResponse(exchange);
        }

        // 查看token 是否过期
        if (JwtUtil.getExpirationTimeFromToken(claims) < System.currentTimeMillis()) {
            return unauthorizedResponse(exchange);
        }

        request.mutate().header(CommonConstants.APX_USER_KEY,JwtUtil.getIdFromToken(claims));
        log.info("{}解析成功，放行{}", request.getURI().getPath());
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse());
    }


    @Override
    public int getOrder() {
        return 0;
    }


}