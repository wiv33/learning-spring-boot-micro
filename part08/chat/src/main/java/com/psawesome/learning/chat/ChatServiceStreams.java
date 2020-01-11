package com.psawesome.learning.chat;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * package: com.psawesome.learning.chat
 * author: PS
 * DATE: 2020-01-12 일요일 00:20
 */
public interface ChatServiceStreams {

    String NEW_COMMENTS = "newComments";
    String CLIENT_TO_BROKER = "clientToBroker";
    String BROKER_TO_CLIENT = "brokerToClient";

    @Input(NEW_COMMENTS)
    SubscribableChannel newComments();

    @Output(CLIENT_TO_BROKER)
    MessageChannel clientToBroker();

    @Input(BROKER_TO_CLIENT)
    SubscribableChannel brokerToClient();
}
