package com.psawesome.learning.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * package: com.psawesome.learning.ops
 * author: PS
 * DATE: 2020-01-11 토요일 12:43
 */
@Component
public class LearningSpringBootHealthIndicator
        implements HealthIndicator {

    @Override
    public Health health() {
        try {
            URL url =
                    new URL("http://greglturnquist.com/learning-spring-boot");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            int statusCode = conn.getResponseCode();
            if (statusCode >= 200 && statusCode < 300) {
                return Health.up().build();
            } else {
                return Health.down()
                        .withDetail("HTTP Status Code", statusCode)
                        .build();
            }
        } catch (IOException e) {
            return Health.down(e).build();
        }
    }
}
