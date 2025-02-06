package com.elyashevich.auth.service;

import com.elyashevich.auth.domain.LoginEntity;
import com.elyashevich.auth.domain.RegisterEntity;

public interface AuthService {

    String login(final LoginEntity loginEntity);

    String register(final RegisterEntity registerEntity);
}
