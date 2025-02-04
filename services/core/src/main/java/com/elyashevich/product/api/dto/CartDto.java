package com.elyashevich.product.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartDto(
        @NotNull(message = "Product id must be not null")
        @Positive(message = "Product id must be positive")
        Long productId,

        @NotNull(message = "User id must be not null")
        @Positive(message = "User id must be positive")
        Long userId
) {
}
