package com.elyashevich.auth.service;

import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;

public interface AuthService {

    String login(final LoginDto loginEntity);

    String register(final RegisterDto registerEntity);

    String verify(final String token);
}
