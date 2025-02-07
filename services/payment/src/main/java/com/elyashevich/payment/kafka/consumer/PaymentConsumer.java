package com.elyashevich.payment.kafka.consumer;

import com.elyashevich.payment.dto.EventType;
import com.elyashevich.payment.dto.OrderStatus;
import com.elyashevich.payment.dto.PaymentEvent;
import com.elyashevich.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "order-topic", groupId = "order-group")
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    public void consumeOrderEvent(final PaymentEvent event) {
        log.debug("Consuming order event: {}", event);

        if (event.getEvent().equals(EventType.CREATE) && event.getStatus().equals(OrderStatus.PENDING)) {
            try {
                this.paymentService.processPayment(event);
            } catch (Exception e) {
                event.setStatus(OrderStatus.REJECTED);
                log.warn(e.getMessage());
            }
        }

        log.info("Consumed order event: {}", event);
    }
}
