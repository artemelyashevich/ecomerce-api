package com.elyashevich.client.rest;

import com.elyashevich.client.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> findAll();

    Product create(final Product product);

    Optional<Product> findById(final Long productId);

    void update(final Long id, final Product product);

    void delete(final Long productId);
}