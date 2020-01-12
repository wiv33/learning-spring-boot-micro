package com.psawesome.learning.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.lang.NonNullApi;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-12 일요일 00:24
 */
@Service
@EnableBinding(ChatServiceStreams.class) // # 해당 컴포넌트를 브로커-처리 장치에 연결
@RequiredArgsConstructor
public class InboundChatService extends UserParsingHandshakeHandler { // # 클라이언트가 연결되면 핸들러 메서드가 호출된다.

    private final ChatServiceStreams chatServiceStreams;

    @Override
    public Mono<Void> handleInternal(WebSocketSession session) {
        // # 이 구현은 잠재적으로 끝없는 WebSocketMessage 객체의 플러스를 제공한다.
        // # (연결된 모든 클라이언트는 이 메서드를 개별적으로 호출)
        return session.receive()
                .log("inbound-incoming-chat-message")
                .map(WebSocketMessage::getPayloadAsText)
                .log("inbound-convert-to-text")
                .map(s -> session.getId() + ":" + s)
                .log("inbound-mark-with-session-id")
                .flatMap(message -> broadcast(message, getUser(session.getId())))
                .log("inbound-broadcast-to-broker")
                .then();
    }

    private Mono<?> broadcast(String message, String user) {
        return Mono.fromRunnable(() ->
                chatServiceStreams.clientToBroker().send(
                MessageBuilder.withPayload(message)
                        /*
                            Message API의 주요 기능 중 일부는 헤더다.
                            표준 헤더를 사용할 수도 있고
                            자신의 필요에 맞게 구성할 수 있다.
                            다른 유용한 세부 정보에는 생성된 타임스탬프 및 원본 주소가 포함될 수 있다.
                            (정말로 뭐든지...)
                        */
                        .setHeader(ChatServiceStreams.USER_HEADER, user)
                        .build()
        ));
    }
}
