package com.elyashevich.order.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderDto(

        @NotNull(message = "User id must be not null")
        @Positive(message = "User id must be positive")
        Long userId,

        @NotNull(message = "Product id must be not null")
        @Positive(message = "Product id must be positive")
        Long productId,

        @NotNull(message = "Total amount must be not null")
        @Positive(message = "Total amount must be positive")
        Long totalAmount
) {
}
