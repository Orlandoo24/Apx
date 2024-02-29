package com.hqzl.apx.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqzl.apx.common.domain.Result;
import com.hqzl.apx.common.enums.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ServletUtils {

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        ObjectMapper objectMapper = new ObjectMapper();
        DataBuffer wrap = null;
        try {
            String apxResult = objectMapper.writeValueAsString(Result.failed(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage()));
            wrap = response.bufferFactory().wrap(apxResult.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.writeWith(Mono.just(wrap));
    }
}
