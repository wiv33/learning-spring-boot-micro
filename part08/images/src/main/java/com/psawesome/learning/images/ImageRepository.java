package com.psawesome.learning.images;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * package: com.psawesome.learning.images
 * author: PS
 * DATE: 2020-01-11 토요일 12:41
 */
public interface ImageRepository
        extends ReactiveCrudRepository<Image, String> {

    Mono<Image> findByName(String name);
}
