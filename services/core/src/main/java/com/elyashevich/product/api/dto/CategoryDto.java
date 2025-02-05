package com.elyashevich.product.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryDto(

        Long id,

        @NotNull(message = "Category name must be not null")
        String name
) {
}
