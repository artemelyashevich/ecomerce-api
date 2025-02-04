package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.ProductDto;
import com.elyashevich.product.api.mapper.ProductMapper;
import com.elyashevich.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        var products = this.productService.findAll();

        return ResponseEntity.ok(this.productMapper.toDto(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(final @PathVariable("id") Long id) {
        var product = this.productService.findById(id);

        return ResponseEntity.ok(this.productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(
            final @Valid @RequestBody ProductDto productDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var candidate = this.productMapper.toEntity(productDto);

        var product = this.productService.create(candidate);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/products/{productId}")
                                .build(Map.of("productId", product.getId()))
                )
                .body(this.productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(
            final @PathVariable("id") Long id,
            final @Valid @RequestBody ProductDto productDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var candidate = this.productMapper.toEntity(productDto);

        var product = this.productService.update(id, candidate);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/products/{productId}")
                                .build(Map.of("productId", product.getId()))
                )
                .body(this.productMapper.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
