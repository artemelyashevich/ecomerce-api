package com.elyashevich.product.service.impl;

import com.elyashevich.product.api.client.ImageRestClient;
import com.elyashevich.product.domain.entity.Category;
import com.elyashevich.product.domain.entity.Product;
import com.elyashevich.product.exception.ResourceNotFoundException;
import com.elyashevich.product.repository.ProductRepository;
import com.elyashevich.product.service.CategoryService;
import com.elyashevich.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_WITH_ID_NOT_FOUND_TEMPLATE = "Product with id '%d' not found";

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ImageRestClient imageRestClient;

    @Override
    public List<Product> findAll(final Integer page, final Integer size) {
        log.debug("Attempting to find all products, page: {}, size: {}", page, size);

        var pageable = PageRequest.of(page, size);

        var productPage = this.productRepository.findAll(pageable);

        log.info("Found {} products on page {} with size {}", productPage.getTotalElements(), page, size);

        return productPage.getContent();
    }

    @Override
    public Product findById(final Long id) {
        log.debug("Attempting to find product by id: {}", id);

        var product = this.productRepository.findById(id).orElseThrow(
                () -> {
                    var message = PRODUCT_WITH_ID_NOT_FOUND_TEMPLATE.formatted(id);
                    log.warn(message);

                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found product with id {}", id);
        return product;
    }

    // FIXME
    @Transactional
    @Override
    public Product create(final Product product) {
        log.debug("Attempting to create product: {}", product);
        Category category = null;
        if (product.getCategory().getId() != null) {
            category = this.categoryService.findById(product.getCategory().getId());
        } else {
            category = this.categoryService.findByName(product.getCategory().getName());
        }
        product.setCategory(category);
        var newProduct = this.productRepository.save(product);

        log.info("Created product with id {}", newProduct.getId());
        return newProduct;
    }

    @Transactional
    @Override
    public Product update(final Long id, final Product product) {
        log.debug("Attempting to update product with id: {}", id);

        Category category = null;
        if (product.getCategory().getId() != null) {
            category = this.categoryService.findById(product.getCategory().getId());
        } else {
            category = this.categoryService.findByName(product.getCategory().getName());
        }

        var oldProduct = this.findById(id);
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategory(category);

        var newProduct = this.productRepository.save(oldProduct);

        log.info("Updated product with id {}", newProduct.getId());
        return newProduct;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        log.debug("Attempting to delete product with id: {}", id);

        var product = this.findById(id);
        this.productRepository.delete(product);

        log.info("Deleted product with id {}", id);
    }

    @Override
    public void uploadImage(final Long id, final MultipartFile file) {
        log.debug("Attempting to add file");

        var product = this.findById(id);
        var imageId = this.imageRestClient.upload(file).id();
        product.setImage(imageId);
        this.productRepository.save(product);

        log.info("File has been added");
    }
}
