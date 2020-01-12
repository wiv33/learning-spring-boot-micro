package com.psawesome.learning.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-12 일요일 00:34
 */
@Service
@EnableBinding(ChatServiceStreams.class)
@Slf4j
public class OutboundChatService extends UserParsingHandshakeHandler {

    private Flux<Message<String>> flux;
    private FluxSink<Message<String>> chatMessageSink;

    public OutboundChatService() {
        this.flux = Flux.<Message<String>>create(
                emitter -> this.chatMessageSink = emitter,
                FluxSink.OverflowStrategy.IGNORE)
                .publish()
                .autoConnect();
    }

    @StreamListener(ChatServiceStreams.BROKER_TO_CLIENT)
    public void listen(Message<String> message) {
        if (Objects.nonNull(chatMessageSink)) {
            log.info("Publishing {} to websocket...", message);
            chatMessageSink.next(message);
        }
    }

    @Override
    protected Mono<Void> handleInternal(WebSocketSession session) {
        return session.send(this.flux.
                filter(s -> validate(s, getUser(session.getId())))
                .map(this::transform)
                .map(session::textMessage)
        .log(getUser(session.getId()) + "-outbound-wrap-as-websocket-message")
        .log(getUser(session.getId()) + "-outbound-publish-to-websocket"));
    }

    private String transform(Message<String> message) {
        String user = message.getHeaders().get(ChatServiceStreams.USER_HEADER, String.class);
        if (message.getPayload().startsWith("@")) {
            return "(" + user + "):" + message.getPayload();
        } else {
            return "(" + user + ")(all):" + message.getPayload();
        }
    }

    private boolean validate(Message<String> message, String user) {
        String payload = message.getPayload();
        if (payload.startsWith("@")) {
            /* @로 시작하는 메시지는 @와 첫 번째 공백 사이의 텍스트 구문을 분석해 대상 사용자 추출한다. */
            String targetUser = payload.substring(1, payload.indexOf(" "));

            /* 현재 사용자가 발신자이거나 수신자인 경우 메시지는 허용되고 그렇지 않으면 삭제 */
            String sender = message.getHeaders().get(ChatServiceStreams.USER_HEADER, String.class);

            return user.equals(sender) || user.equals(targetUser);

        } else {
            return true;
        }
    }

}
