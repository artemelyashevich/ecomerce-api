package com.elyashevich.payment.kafka.producer;

import com.elyashevich.payment.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void sendOrderEvent(final PaymentEvent event) {
        log.debug("Sending order event: {}", event);

        kafkaTemplate.send("order-topic", event);

        log.info("Order event sent {}", event);
    }

}
