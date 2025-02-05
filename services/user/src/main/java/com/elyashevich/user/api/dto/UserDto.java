package com.elyashevich.user.api.dto;

import com.elyashevich.user.domain.entity.Role;

import java.util.Set;

public record UserDto(
        Long id,
        String username,
        String fullName,
        String email,
        String address,
        Set<Role> roles
) {
}
