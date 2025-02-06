package com.elyashevich.auth.api.client;

import com.elyashevich.auth.domain.RegisterEntity;
import com.elyashevich.auth.domain.UserEntity;

public interface UserRestClient {

    UserEntity findUserByEmail(final String email);

    UserEntity saveUser(final RegisterEntity user);
}
