package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.Product;
import com.elyashevich.client.rest.ProductRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProductRestClientImpl implements ProductRestClient {

    private static final ParameterizedTypeReference<List<Product>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Product> findAll() {
        log.debug("Attempting to fetch all products");

        var products = this.restClient
                .get()
                .retrieve()
                .body(PARAMETERIZED_TYPE_REFERENCE);

        log.info("All products have been fetched");
        return products;
    }

    @Override
    public Product create(final Product product) {
        log.debug("Attempting to create a new product");

        var newProduct = this.restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .body(Product.class);

        log.info("New product created");
        return newProduct;
    }

    @Override
    public Product findById(final Long productId) {
        log.debug("Attempting to fetch product by id");

        var product = this.restClient
                .get()
                .uri("/{productId}", productId)
                .retrieve()
                .body(Product.class);

        log.info("Product with id {} found", productId);
        return product;
    }

    @Override
    public void update(final Long id, final Product product) {
        log.debug("Attempting to update product with id {}", id);

        this.restClient
                .put()
                .uri("/{productId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .toBodilessEntity();

        log.info("Product with id {} updated", id);
    }

    @Override
    public void delete(final Long productId) {
        log.debug("Attempting to delete product with id {}", productId);

        this.restClient
                .delete()
                .uri("/{productId}", productId)
                .retrieve()
                .toBodilessEntity();

        log.info("Product with id {} deleted", productId);
    }
}
