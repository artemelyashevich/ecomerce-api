package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.Category;
import com.elyashevich.client.rest.CategoryRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CategoryRestClientImpl implements CategoryRestClient {

    private static final ParameterizedTypeReference<List<Category>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Category> findAll() {
        log.debug("Attempting to fetch all products");

        var categories = this.restClient
                .get()
                .retrieve()
                .body(PARAMETERIZED_TYPE_REFERENCE);

        log.info("All products have been fetched");
        return categories;
    }

    @Override
    public Category create(Category category) {
        return this.restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(category)
                .retrieve()
                .body(Category.class);
    }

    @Override
    public Category findById(Long categoryId) {
        return this.restClient
                .get()
                .uri("/{categoryId}", categoryId)
                .retrieve()
                .body(Category.class);
    }

    @Override
    public void update(Long categoryId, Category category) {
        this.restClient
                .patch()
                .uri("/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(category)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void delete(Long categoryId) {
        this.restClient
                .delete()
                .uri("/{productId}", categoryId)
                .retrieve()
                .toBodilessEntity();
    }
}
