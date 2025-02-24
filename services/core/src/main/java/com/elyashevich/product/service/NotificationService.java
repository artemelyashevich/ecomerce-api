package com.elyashevich.product.service;

import com.elyashevich.product.domain.event.NotificationEvent;

public interface NotificationService {

    void sendNotification(final NotificationEvent event);
}
