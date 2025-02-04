package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(final Long id);

    Product create(final Product product);

    Product update(final Long id, final Product product);

    void delete(final Long id);
}
