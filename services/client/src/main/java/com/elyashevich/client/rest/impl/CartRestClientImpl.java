package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.Cart;
import com.elyashevich.client.rest.CartRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartRestClientImpl implements CartRestClient {

    private final RestClient restClient;

    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public Cart create(Cart cart) {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return Optional.empty();
    }

    @Override
    public void update(Long cartId, Cart cart) {

    }

    @Override
    public void delete(Long cartId) {

    }
}
