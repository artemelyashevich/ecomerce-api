package com.elyashevich.auth.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTokenRequest {

    @NotNull(message = "Token must be not null")
    @NotEmpty(message = "Token must be not empty")
    @NotBlank(message = "Token must be not blank")
    private String token;
}
