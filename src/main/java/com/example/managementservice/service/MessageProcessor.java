package com.example.managementservice.service;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

import java.util.UUID;

public class MessageProcessor {

    // user id is set as messageId
    public static MessagePostProcessor buildMessageProperties(String userId) {
        UUID correlationId = UUID.randomUUID();
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties
                    = message.getMessageProperties();
            messageProperties.setCorrelationId(correlationId.toString());
            messageProperties.setMessageId(userId);
        return message;
        };

        return messagePostProcessor;
    }
}
