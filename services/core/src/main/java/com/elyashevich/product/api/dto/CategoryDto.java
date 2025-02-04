package com.elyashevich.product.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryDto(

        @NotNull(message = "Category id must be not null")
        @Positive(message = "Category id must be positive")
        Long id,

        @NotNull(message = "Category name must be not null")
        String name
) {
}
