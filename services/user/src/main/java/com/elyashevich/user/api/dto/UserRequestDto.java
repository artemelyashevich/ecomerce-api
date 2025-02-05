package com.elyashevich.user.api.dto;


import com.elyashevich.user.api.dto.contract.Validate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserRequestDto(
        Long id,

        @NotNull(message = "Username must be not null", groups = Validate.class)
        @NotEmpty(message = "Username must be not empty", groups = Validate.class)
        String username,

        @NotNull(message = "Email must be not null")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Address must be not null", groups = Validate.class)
        @NotEmpty(message = "Address must be not empty", groups = Validate.class)
        String address,

        @NotNull(message = "Password must be not null")
        @NotEmpty(message = "Password must be not empty")
        @Length(min = 8,
                max = 255,
                message = "Password must be in {min} and {max}")
        String password
) {
}
