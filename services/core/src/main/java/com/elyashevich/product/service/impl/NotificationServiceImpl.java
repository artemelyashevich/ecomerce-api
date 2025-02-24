package com.elyashevich.product.service.impl;

import com.elyashevich.product.domain.event.NotificationEvent;
import com.elyashevich.product.service.NotificationService;
import com.elyashevich.product.util.WebSocketConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(final NotificationEvent event) {
        this.messagingTemplate.convertAndSend(WebSocketConstant.DESTINATION_PREFIX, event);
    }
}
