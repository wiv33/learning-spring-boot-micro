package com.psawesome.learning.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * package: com.psawesome.learning.eureka
 * author: PS
 * DATE: 2020-01-11 토요일 12:35
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                EurekaApplication.class);
    }
}
