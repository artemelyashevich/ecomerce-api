package com.elyashevich.product.service.impl;

import com.elyashevich.product.domain.entity.Category;
import com.elyashevich.product.exception.ResourceAlreadyExistsException;
import com.elyashevich.product.exception.ResourceNotFoundException;
import com.elyashevich.product.repository.CategoryRepository;
import com.elyashevich.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public static final String CATEGORY_WITH_ID_NOT_FOUND_TEMPLATE = "Category with id '%d' not found";
    public static final String CATEGORY_WITH_NAME_WAS_NOT_FOUND_TEMPLATE = "Category with name '%s' was not found";

    private final CategoryRepository categoryRepository;

    @Cacheable(value="CategoryService::findAll")
    @Override
    public List<Category> findAll() {
        log.debug("Attempting to find all categories");

        var categories = this.categoryRepository.findAll();

        log.info("Found {} categories", categories.size());
        return categories;
    }

    @Cacheable(value="CategoryService::findById", key = "#id")
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

    @Caching(
            put = {
                    @CachePut(value = "CategoryService::findById", key="#result.id"),
                    @CachePut(value = "CategoryService::findByName", key="#result.name"),
                    @CachePut(value = "CategoryService::findAll")
            }
    )
    @Override
    public Category create(final Category category) {
        log.debug("Attempting to create category: {}", category);

        var newCategory = this.categoryRepository.save(category);

        log.info("Created category with id {}", newCategory.getId());
        return newCategory;
    }

    @Caching(
            put = {
                    @CachePut(value = "CategoryService::findById", key="#id"),
                    @CachePut(value = "CategoryService::findByName", key="#category.name"),
                    @CachePut(value = "CategoryService::findAll")
            }
    )
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

    @CacheEvict(value = "ProductService::findById", key = "#id")
    @Transactional
    @Override
    public void delete(final Long id) {
        log.debug("Attempting to delete category with id: {}", id);

        var category = this.findById(id);
        this.categoryRepository.delete(category);

        log.info("Deleted category with id {}", id);
    }

    @Cacheable(value = "CategoryService::findByName", key="#name")
    @Override
    public Category findByName(final String name) {
        log.debug("Attempting to find category by name: {}", name);

        var category = this.categoryRepository.findByName(name).orElseThrow(
                ()-> {
                    var message = CATEGORY_WITH_NAME_WAS_NOT_FOUND_TEMPLATE.formatted(name);

                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found category with name {}", name);
        return category;
    }
}