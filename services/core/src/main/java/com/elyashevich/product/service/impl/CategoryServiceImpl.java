package com.elyashevich.product.service.impl;

import com.elyashevich.product.domain.entity.Category;
import com.elyashevich.product.exception.ResourceAlreadyExistsException;
import com.elyashevich.product.exception.ResourceNotFoundException;
import com.elyashevich.product.repository.CategoryRepository;
import com.elyashevich.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_WITH_ID_NOT_FOUND_TEMPLATE = "Category with id '%d' not found";
    public static final String CATEGORY_WITH_NAME_ALREADY_EXISTS_TEMPLATE = "Category with name '%s' already exists";

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        log.debug("Attempting to find all categories");

        var categories = this.categoryRepository.findAll();

        log.info("Found {} categories", categories.size());
        return categories;
    }

    @Override
    public Category findById(final Long id) {
        log.debug("Attempting to find category by id: {}", id);

        var category = this.categoryRepository.findById(id).orElseThrow(
                () -> {
                    var message = CATEGORY_WITH_ID_NOT_FOUND_TEMPLATE.formatted(id);
                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found category with id {}", id);
        return category;
    }

    @Override
    public Category create(final Category category) {
        log.debug("Attempting to create category: {}", category);

        /*if (this.categoryRepository.existsByName(category.getName())) {
            var message = CATEGORY_WITH_NAME_ALREADY_EXISTS_TEMPLATE.formatted(category.getName());

            log.warn(message);

            throw new ResourceAlreadyExistsException(message);
        }*/
        var newCategory = this.categoryRepository.save(category);

        log.info("Created category with id {}", newCategory.getId());
        return newCategory;
    }

    @Transactional
    @Override
    public Category update(final Long id, final Category category) {
        log.debug("Attempting to update category with id: {}", id);

        var oldCategory = this.findById(id);
        oldCategory.setName(category.getName());

        var newCategory = this.categoryRepository.save(oldCategory);

        log.info("Updated category with id {}", newCategory.getId());
        return newCategory;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        log.debug("Attempting to delete category with id: {}", id);

        var category = this.findById(id);
        this.categoryRepository.delete(category);

        log.info("Deleted category with id {}", id);
    }
}