package com.elyashevich.order.kafka.consumer;

import com.elyashevich.order.domain.EventType;
import com.elyashevich.order.domain.OrderEvent;
import com.elyashevich.order.domain.OrderStatus;
import com.elyashevich.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void consumeOrderEvent(final String message) {
        var objectMapper = new ObjectMapper();

        var event = objectMapper.convertValue(message, OrderEvent.class);

        log.debug("Consuming order event: {}", event);

        if (event.getEvent().equals(EventType.DELETE) && event.getStatus().equals(OrderStatus.REJECTED)) {
            this.orderService.delete(event.getOrderId());
        }
        if (event.getEvent().equals(EventType.UPDATE) && event.getStatus().equals(OrderStatus.FULFILLED)) {
            this.orderService.complete(event.getOrderId());
        }

        log.info("Consumed order event: {}", event);
    }
}