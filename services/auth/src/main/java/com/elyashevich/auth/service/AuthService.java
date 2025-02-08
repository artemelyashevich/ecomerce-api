package com.elyashevich.auth.service;

import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;

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
    String login(final LoginDto loginEntity);

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerEntity the registration details
     * @return a token for the newly registered user
     */
    String register(final RegisterDto registerEntity);

    /**
     * Verifies a user's token.
     *
     * @param token the token to verify
     * @return the result of the token verification
     */
    String verify(final String token);
}
