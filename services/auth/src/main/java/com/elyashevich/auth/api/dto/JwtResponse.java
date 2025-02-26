package com.elyashevich.auth.api.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
