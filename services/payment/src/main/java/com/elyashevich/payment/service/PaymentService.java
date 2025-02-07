package com.elyashevich.payment.service;


import com.elyashevich.payment.dto.PaymentEvent;

public interface PaymentService {

    void processPayment(final PaymentEvent event) throws InterruptedException;

    void rejectPayment(final PaymentEvent event);
}
