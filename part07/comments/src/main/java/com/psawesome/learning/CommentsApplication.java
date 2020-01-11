package com.psawesome.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * package: com.psawesome.learning
 * author: PS
 * DATE: 2020-01-11 토요일 12:23
 */
@SpringBootApplication
@EnableEurekaClient
public class CommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }
}
