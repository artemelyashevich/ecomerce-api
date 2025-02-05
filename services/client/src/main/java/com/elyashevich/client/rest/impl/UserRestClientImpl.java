package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.User;
import com.elyashevich.client.rest.UserRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRestClientImpl implements UserRestClient {

    private final RestClient restClient;


    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.empty();
    }

    @Override
    public void update(Long userId, User cart) {

    }

    @Override
    public void delete(Long userId) {

    }
}
