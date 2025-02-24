package com.elyashevich.product.kafka;

import com.elyashevich.product.domain.event.NotificationEvent;
import com.elyashevich.product.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consumeOrderEvent(final String message) throws JsonProcessingException {

        var objectMapper = new ObjectMapper();
        var event = objectMapper.readValue(message, NotificationEvent.class);

        log.debug("Consuming notification event: {}", event);

        this.notificationService.sendNotification(event);

        log.info("Consumed notification event: {}", event);
    }
}