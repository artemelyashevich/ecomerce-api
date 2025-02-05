package com.elyashevich.client.rest;

import com.elyashevich.client.domain.Cart;
import com.elyashevich.client.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CartRestClient {

    List<Cart> findAll();

    Cart create(final Cart cart);

    Optional<Cart> findById(final Long cartId);

    void update(final Long cartId, final Cart cart);

    void delete(final Long cartId);
}
