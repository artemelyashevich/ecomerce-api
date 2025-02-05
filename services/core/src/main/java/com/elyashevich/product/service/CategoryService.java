package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(final Long id);

    Category create(final Category category);

    Category update(final Long id, final Category category);

    void delete(final Long id);

    Category findByName(final String name);
}
