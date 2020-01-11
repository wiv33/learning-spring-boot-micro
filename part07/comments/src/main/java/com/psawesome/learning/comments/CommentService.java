package com.psawesome.learning.comments;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * package: com.psawesome.learning.comments
 * author: PS
 * DATE: 2020-01-11 토요일 12:34
 */
@Service
@EnableBinding(Processor.class)
public class CommentService {
    // end::stream-1[]

    private final CommentRepository repository;

    private final MeterRegistry meterRegistry;

    public CommentService(CommentRepository repository,
                          MeterRegistry meterRegistry) {
        this.repository = repository;
        this.meterRegistry = meterRegistry;
    }

    // tag::stream-2[]
    @StreamListener
    @Output(Processor.OUTPUT)
    public Flux<Void> save(@Input(Processor.INPUT) Flux<Comment> newComment) {
        return repository
                .saveAll(newComment)
                .flatMap(comment -> {
                    meterRegistry
                            .counter("comments.consumed", "imageId", comment.getImageId())
                            .increment();
                    return Mono.empty();
                });
    }
    // end::stream-2[]

    @Bean
    CommandLineRunner setUp(CommentRepository repository) {
        return args -> {
            repository.deleteAll().subscribe();
        };
    }
}
