package com.psawesome.learning.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-11 토요일 23:01
 */
@Configuration
public class WebSocketConfig {

    /*
        @Bean HandlerMapping
        핸들러 메서드와 라우트를 연결하기 위한 스프링 인터페이스

        이 메서드가 웹소켓 메시지 처리를 위한 경로를 연결한다는 것
    */
    @Bean
    HandlerMapping webSocketMapping(CommentService commentService) {
        /*
            /topic/comments.new 를 사용해 맵을 로드하고
            WebSocketHandler 인터페이스를 구현하는 클래스와 연결

        */
        Map<String, WebSocketHandler> urlMap = new HashMap<>();
        urlMap.put("/topic/comments.new", commentService);


        /*
            Cross-origin Resource Sharing
            CORS 정책 구현
        */
        Map<String, CorsConfiguration> corsConfigurationMap = new HashMap<>();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfigurationMap.put("/topic/comments.new", corsConfiguration);

        /* 스프링 부트의 자동 설정에 정의된 다른 라우트 핸들러보다 먼저 볼 수 있는 설정(Order)를 추가 */
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(10);
        mapping.setUrlMap(urlMap);
        mapping.setCorsConfigurations(corsConfigurationMap);

        return mapping;
    }

    /*
        스프링의 DispatcherHandler를 WebSocketHandler에 연결해
        URI를 핸들러 메서드에 매핑할 수 있도록 한다.
    */
    @Bean
    WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
