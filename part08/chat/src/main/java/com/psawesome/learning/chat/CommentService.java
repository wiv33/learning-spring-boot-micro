package com.psawesome.learning.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-11 토요일 22:59
 */
@Service
@EnableBinding(Sink.class) // 스프링 클라우스 스트림 메시지의 수신자로 보여준다.
@Slf4j
public class CommentService implements WebSocketHandler { // WebFlux 인터페이스인 WebSocketHandler

    private ObjectMapper mapper;
    private Flux<Comment> flux;
    private FluxSink<Comment> webSocketCommentSink;

    public CommentService(ObjectMapper mapper) {
        this.mapper = mapper;
        this.flux = Flux.create(CommentService.this::accept,
                FluxSink.OverflowStrategy.IGNORE)
                .publish()
                .autoConnect();
    }

    private void accept(FluxSink<Comment> emitter) {
        this.webSocketCommentSink = emitter;
    }

    // INPUT에 대한 StreamListener
    // application/json 설정으로 comment 객체로 역직렬화 된다.
    @StreamListener(Sink.INPUT)
    public void broadcase(Comment comment) {
        if (Objects.nonNull(webSocketCommentSink)) {
            log.info("Publishing {} to websocket...", comment.toString());
            webSocketCommentSink.next(comment);
        }
    }

    /*
        WebFlux를 웹 소켓 세션을 통해 푸시하는 것
    */
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                this.flux.map(comment -> {
                    try {
                        return mapper.writeValueAsString(comment);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
            .log("encode-as-json")
            .map(session::textMessage)
            .log("wrap-as-websocket-message"))
        .log("publish-to-websocket");
    }
}
