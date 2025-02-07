package com.elyashevich.order.kafka.consumer;

import com.elyashevich.order.domain.EventType;
import com.elyashevich.order.domain.OrderEvent;
import com.elyashevich.order.domain.OrderStatus;
import com.elyashevich.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "payment-topic", groupId = "payment-group")
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    public void consumeOrderEvent(final OrderEvent event) {
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