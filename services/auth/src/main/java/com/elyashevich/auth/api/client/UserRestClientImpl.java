package com.elyashevich.auth.api.client;

import com.elyashevich.auth.domain.RegisterEntity;
import com.elyashevich.auth.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
public class UserRestClientImpl implements UserRestClient {

    private final RestClient restClient;

    @Override
    public UserEntity findUserByEmail(final String email) {
        log.debug("Fetching user by email '{}'", email);

        var user = this.restClient
                .get()
                .uri("/email/{email}", email)
                .retrieve()
                .body(UserEntity.class);

        log.info("User fetched {}", user.getId());
        return user;
    }

    @Override
    public UserEntity saveUser(final RegisterEntity user) {
        log.debug("Saving user '{}'", user.getEmail());

        var newUser = this.restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(UserEntity.class);

        log.info("User saved {}", newUser.getId());
        return newUser;
    }
}
