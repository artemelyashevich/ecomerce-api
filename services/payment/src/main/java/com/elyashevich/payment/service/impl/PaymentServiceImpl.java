package com.elyashevich.payment.service.impl;

import com.elyashevich.payment.dto.EventType;
import com.elyashevich.payment.dto.OrderStatus;
import com.elyashevich.payment.dto.PaymentEvent;
import com.elyashevich.payment.kafka.producer.PaymentProducer;
import com.elyashevich.payment.service.PaymentService;
import com.elyashevich.payment.util.RandomOrderStatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentProducer paymentProducer;

    private static final int DELAY_TO_RETRIEVE_LOCKED_STATUS = 5000;

    @Override
    public void processPayment(final PaymentEvent event) throws InterruptedException {
        log.debug("Processing payment event: {}", event);

        TimeUnit.MILLISECONDS.sleep(DELAY_TO_RETRIEVE_LOCKED_STATUS);
        event.setEvent(EventType.UPDATE);
        event.setStatus(RandomOrderStatusUtil.getRandomOrderStatus());
        this.paymentProducer.sendOrderEvent(event);
    }

    @Override
    public void rejectPayment(final PaymentEvent event) {
        log.debug("Rejecting payment event: {}", event);

        this.paymentProducer.sendOrderEvent(event);

        log.info("Event rejected: {}", event);
    }
}
