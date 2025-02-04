package com.elyashevich.product.service.impl;

import com.elyashevich.product.domain.entity.Cart;
import com.elyashevich.product.exception.ResourceNotFoundException;
import com.elyashevich.product.repository.CartRepository;
import com.elyashevich.product.service.CartService;
import com.elyashevich.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    public static final String CART_WITH_ID_NOT_FOUND_TEMPLATE = "Cart with id '%d' not found";

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public List<Cart> finaAllByUserId(final Long userId) {
        log.debug("Attempting to find all carts by user id: {}", userId);

        var carts = this.cartRepository.findAllByUserId(userId);

        log.info("Found {} carts for user with id {}", carts.size(), userId);
        return carts;
    }

    @Override
    public Cart findById(final Long id) {
        log.debug("Attempting to find cart by id: {}", id);

        var cart = this.cartRepository.findById(id).orElseThrow(
                () -> {
                    var message = CART_WITH_ID_NOT_FOUND_TEMPLATE.formatted(id);
                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found cart with id {}", id);
        return cart;
    }

    @Transactional
    @Override
    public Cart create(final Cart cart) {
        log.debug("Attempting to create cart: {}", cart);

        var product = this.productService.findById(cart.getProduct().getId());
        cart.setProduct(product);
        var newCart = this.cartRepository.save(cart);

        log.info("Created cart with id {}", newCart.getId());
        return newCart;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        log.debug("Attempting to delete cart with id: {}", id);

        var cart = this.findById(id);
        this.cartRepository.delete(cart);

        log.info("Deleted cart with id {}", id);
    }

    @Override
    public void deleteAllByUserId(final Long userId) {
        log.debug("Attempting to delete all carts for user with id: {}", userId);

        this.cartRepository.deleteAllByUserId(userId);

        log.info("Deleted all carts for user with id {}", userId);
    }
}
