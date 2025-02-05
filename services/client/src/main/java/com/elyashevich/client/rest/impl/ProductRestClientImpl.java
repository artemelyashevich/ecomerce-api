package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.Product;
import com.elyashevich.client.rest.ProductRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class ProductRestClientImpl implements ProductRestClient {

    private static final ParameterizedTypeReference<List<Product>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Product> findAllProducts() {
        log.debug("Attempting to fetch all products");

        var products = this.restClient
                .get()
                .uri("/api/v1/products")
                .retrieve()
                .body(PARAMETERIZED_TYPE_REFERENCE);

        log.info("All products have been fetched");
        return products;
    }

    @Override
    public Product createProduct(final Product product) {
        return this.restClient
                .post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .body(Product.class);
    }

    @Override
    public Optional<Product> findProductById(final Long productId) {
        return Optional.ofNullable(this.restClient
                .get()
                .uri("/api/v1/products/{productId}", productId)
                .retrieve()
                .body(Product.class)
        );
    }

    @Override
    public void updateProductById(final Long id, final Product product) {
        this.restClient
                .patch()
                .uri("/api/v1/products/{productId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void deleteProduct(final Long productId) {
        this.restClient
                .delete()
                .uri("/api/v1/products/{productId}", productId)
                .retrieve()
                .toBodilessEntity();
    }
}
