package com.elyashevich.order.kafka.producer;

import com.elyashevich.order.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {
        log.debug("Sending order event: {}", event);

        kafkaTemplate.send("order-topic", event);

        log.info("Order event sent {}", event);
    }
}