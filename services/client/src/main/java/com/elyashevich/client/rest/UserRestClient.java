package com.elyashevich.client.rest;

import com.elyashevich.client.domain.Cart;
import com.elyashevich.client.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRestClient {
    List<User> findAll();

    User create(final User user);

    Optional<User> findById(final Long userId);

    void update(final Long userId, final User cart);

    void delete(final Long userId);
}
