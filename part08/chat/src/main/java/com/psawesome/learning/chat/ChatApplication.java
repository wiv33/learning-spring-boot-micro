package com.psawesome.learning.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-11 토요일 23:07
 */
@SpringBootApplication
@EnableEurekaClient
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
