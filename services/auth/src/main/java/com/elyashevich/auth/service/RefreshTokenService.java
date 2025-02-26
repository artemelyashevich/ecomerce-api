package com.elyashevich.auth.service;

import com.elyashevich.auth.domain.RefreshToken;

public interface RefreshTokenService {

    void save(final RefreshToken refreshToken);

    String findByUserEmail(final String userEmail);
}
