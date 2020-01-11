package com.psawesome.learning.comments;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * package: com.psawesome.learning.comments
 * author: PS
 * DATE: 2020-01-11 토요일 12:33
 */
public interface CommentRepository
        extends Repository<Comment, String> {

    Flux<Comment> findByImageId(String imageId);

    Flux<Comment> saveAll(Flux<Comment> newComment);

    // Required to support save()
    Mono<Comment> findById(String id);

    Mono<Void> deleteAll();
}
