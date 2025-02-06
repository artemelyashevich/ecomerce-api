package com.elyashevich.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    @NotNull(message = "Email must be not null")
    @NotEmpty(message = "Email must be not empty")
    @NotBlank(message = "Email must be not blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password must be not null")
    @NotEmpty(message = "Password must be not empty")
    @NotBlank(message = "Password must be not blank")
    @Length(min = 8, max = 255, message = "Password must be in {min} and {max}")
    private String password;
}
