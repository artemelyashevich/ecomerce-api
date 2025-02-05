package com.elyashevich.client.rest;

import com.elyashevich.client.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {

    List<Product> findAllProducts();

    Product createProduct(final Product product);

    Optional<Product> findProductById(final Long productId);

    void updateProductById(final Long id, final Product product);

    void deleteProduct(final Long productId);
}