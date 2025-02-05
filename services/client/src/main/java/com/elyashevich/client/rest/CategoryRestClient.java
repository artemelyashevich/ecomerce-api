package com.elyashevich.client.rest;

import com.elyashevich.client.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRestClient {

    List<Category> findAll();

    Category create(final Category category);

    Category findById(final Long categoryId);

    void update(final Long categoryId, final Category category);

    void delete(final Long categoryId);
}
