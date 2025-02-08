package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Cart;

import java.util.List;

/**
 * Service interface for managing shopping carts.
 */
public interface CartService {

    /**
     * Retrieves all carts associated with a specific user ID.
     *
     * @param userId the ID of the user
     * @return a list of carts associated with the specified user ID
     */
    List<Cart> finaAllByUserId(final Long userId);

    /**
     * Retrieves a cart by its ID.
     *
     * @param id the ID of the cart
     * @return the cart with the specified ID
     */
    Cart findById(final Long id);

    /**
     * Creates a new cart.
     *
     * @param cart the cart to create
     * @return the created cart
     */
    Cart create(final Cart cart);

    /**
     * Deletes a cart by its ID.
     *
     * @param id the ID of the cart to delete
     */
    void delete(final Long id);

    /**
     * Deletes all carts associated with a specific user ID.
     *
     * @param userId the ID of the user
     */
    void deleteAllByUserId(final Long userId);
}
