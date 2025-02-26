package com.elyashevich.auth.api.dto;

public record ResetPasswordDto(
        String oldPassword,
        String newPassword
) {
}
