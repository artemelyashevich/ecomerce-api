package com.elyashevich.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent {

    @NotNull(message = "Order id must be not null")
    private Long orderId;

    @NotNull(message = "Event must be not null")
    private EventType event;


    @NotNull(message = "Status must be not null")
    private OrderStatus status;
}
