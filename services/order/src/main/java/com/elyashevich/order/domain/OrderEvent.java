package com.elyashevich.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {

    private Long orderId;

    private EventType event;

    private OrderStatus status;
}
