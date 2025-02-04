package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> finaAllByUserId(final Long userId);

    Cart findById(final Long id);

    Cart create(final Cart cart);

    void delete(final Long id);

    void deleteAllByUserId(final Long userId);
}
