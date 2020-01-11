package com.psawesome.learning.images;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * package: com.psawesome.learning.images
 * author: PS
 * DATE: 2020-01-11 토요일 12:40
 */
@Configuration
public class Config {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}