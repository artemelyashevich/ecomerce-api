package com.elyashevich.auth.service;

import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.api.dto.ResetPasswordDto;
import com.elyashevich.auth.api.dto.JwtResponse;

/**
 * Service interface for authentication and authorization.
 */
public interface AuthService {

    /**
     * Logs in a user with the provided login details.
     *
     * @param loginEntity the login details
     * @return a token for the authenticated user
     */
    JwtResponse login(final LoginDto loginEntity);

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerEntity the registration details
     * @return a token for the newly registered user
     */
    JwtResponse register(final RegisterDto registerEntity);

    JwtResponse refresh(final String token);

    void resetPassword(final ResetPasswordDto resetPasswordDto, final String email);
    /**
     * Verifies a user's token.
     *
     * @param token the token to verify
     * @return the result of the token verification
     */
    String verify(final String token);
}
