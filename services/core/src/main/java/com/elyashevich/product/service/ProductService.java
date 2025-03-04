package com.elyashevich.product.service;

import com.elyashevich.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    Page<Product> findAll(final Integer page, final Integer size);

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return the product with the specified ID
     */
    Product findById(final Long id);

    /**
     * Creates a new product.
     *
     * @param product the product to create
     * @return the created product
     */
    Product create(final Product product);

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update
     * @param product the updated product information
     * @return the updated product
     */
    Product update(final Long id, final Product product);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    void delete(final Long id);

    void uploadImage(final Long id, final MultipartFile file);
}
