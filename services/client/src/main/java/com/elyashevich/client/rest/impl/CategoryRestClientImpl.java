package com.elyashevich.client.rest.impl;

import com.elyashevich.client.domain.Category;
import com.elyashevich.client.rest.CategoryRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryRestClientImpl implements CategoryRestClient {

    private final RestClient restClient;

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return Optional.empty();
    }

    @Override
    public void update(Long categoryId, Category category) {

    }

    @Override
    public void delete(Long categoryId) {

    }
}
