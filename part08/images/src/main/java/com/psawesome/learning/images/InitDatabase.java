package com.psawesome.learning.images;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

/**
 * package: com.psawesome.learning.images
 * author: PS
 * DATE: 2020-01-11 토요일 12:41
 */
@Component
public class InitDatabase {
    @Bean
    CommandLineRunner init(MongoOperations operations) {
        return args -> {
            // tag::log[]
            operations.dropCollection(Image.class);

            operations.insert(new Image("1",
                    "learning-spring-boot-cover.jpg"));
            operations.insert(new Image("2",
                    "learning-spring-boot-2nd-edition-cover.jpg"));
            operations.insert(new Image("3",
                    "bazinga.png"));

            operations.findAll(Image.class).forEach(image -> {
                System.out.println(image.toString());
            });
            // end::log[]
        };
    }
}
