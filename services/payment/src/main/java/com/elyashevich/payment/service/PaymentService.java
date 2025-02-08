package com.elyashevich.payment.service;


import com.elyashevich.payment.dto.PaymentEvent;

/**
 * Service interface for processing payments.
 */
public interface PaymentService {

    /**
     * Processes a payment event.
     *
     * @param event the payment event to process
     * @throws InterruptedException if the payment processing is interrupted
     */
    void processPayment(final PaymentEvent event) throws InterruptedException;

    /**
     * Rejects a payment event.
     *
     * @param event the payment event to reject
     */
    void rejectPayment(final PaymentEvent event);
}
