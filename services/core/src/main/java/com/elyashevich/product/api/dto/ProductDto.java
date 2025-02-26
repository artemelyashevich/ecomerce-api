package com.elyashevich.product.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record ProductDto(

        Long id,

        @NotNull(message = "Name must be not empty")
        @Length(
                min = 2,
                max = 50,
                message = "Name must be in {min} and {max}"
        )
        String name,

        @NotNull(message = "Description must be not empty")
        @Length(
                min = 5,
                max = 255,
                message = "Description must be in {min} and {max}"
        )
        String description,

        @Positive(message = "Price must be positive")
        double price,

        @NotNull(message = "Category must be not null")
        CategoryDto category,

        String image
) {
}