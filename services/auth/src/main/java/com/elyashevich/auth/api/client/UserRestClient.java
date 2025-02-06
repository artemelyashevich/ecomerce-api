package com.elyashevich.auth.api.client;

import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.domain.UserEntity;

public interface UserRestClient {

    UserEntity findUserByEmail(final String email);

    UserEntity saveUser(final RegisterDto user);
}
