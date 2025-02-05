package com.elyashevich.client.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;

    @NotNull(message = "Name must be not null")
    @NotBlank(message = "Name must be not blank")
    @NotEmpty(message = "Name must be not empty")
    @Length(min = 2, max = 255, message = "Name must be in {min} and {max}")
    private String name;

    @NotNull(message = "Description must be not null")
    @NotBlank(message = "Description must be not blank")
    @NotEmpty(message = "Description must be not empty")
    @Length(min = 1, max = 255, message = "Description must be in {min} and {max}")
    private String description;

    private Category category;

    @NotNull(message = "Price must be not null")
    @Positive(message = "Price must be positive")
    private Double price;
}
