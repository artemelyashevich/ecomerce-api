package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Category;

import java.util.List;

/**
 * Service interface for managing categories.
 */
public interface CategoryService {

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    List<Category> findAll();

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category
     * @return the category with the specified ID
     */
    Category findById(final Long id);

    /**
     * Creates a new category.
     *
     * @param category the category to create
     * @return the created category
     */
    Category create(final Category category);

    /**
     * Updates an existing category.
     *
     * @param id the ID of the category to update
     * @param category the updated category information
     * @return the updated category
     */
    Category update(final Long id, final Category category);

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     */
    void delete(final Long id);

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category
     * @return the category with the specified name
     */
    Category findByName(final String name);
}
