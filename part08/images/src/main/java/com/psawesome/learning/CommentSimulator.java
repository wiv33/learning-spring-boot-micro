package com.psawesome.learning;

import com.psawesome.learning.images.Comment;
import com.psawesome.learning.images.CommentController;
import com.psawesome.learning.images.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * package: com.psawesome.learning
 * author: PS
 * DATE: 2020-01-11 토요일 12:39
 */
// tag::code[]
@Profile("simulator")
@Component
@RequiredArgsConstructor
public class CommentSimulator {

    private final HomeController homeController;
    private final CommentController controller;
    private final ImageRepository repository;

    private final AtomicInteger counter;


    @EventListener
    public void simulateComments(ApplicationReadyEvent event) {
        Flux
                .interval(Duration.ofMillis(1000))
                .flatMap(tick -> repository.findAll())
                .map(image -> {
                    Comment comment = new Comment();
                    comment.setImageId(image.getId());
                    comment.setComment(
                            "Comment #" + counter.getAndIncrement());
                    return Mono.just(comment);
                })
                .flatMap(newComment ->
                        Mono.defer(() ->
                                controller.addComment(newComment)))
                .subscribe();
    }

    @EventListener
    public void simulateUsersClicking(ApplicationReadyEvent event) {
        Flux
                .interval(Duration.ofMillis(500))
                .flatMap(tick ->
                        Mono.defer(() -> homeController.index(new BindingAwareModelMap())))
                .subscribe();
    }

}

