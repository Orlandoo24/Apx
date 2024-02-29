package com.hqzl.apx.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
 
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 *  网关配置 https 协议
 */
@Configuration
public class HttpServer {

    @Autowired
    private HttpHandler httpHandler;

    private WebServer webServer;

    @PostConstruct
    public void start() {
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(22222);
        webServer = factory.getWebServer(httpHandler);
        webServer.start();
    }

    @PreDestroy
    public void stop() {
        webServer.stop();
    }

}