package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.ProductDto;
import com.elyashevich.product.api.mapper.ProductMapper;
import com.elyashevich.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Endpoints for managing products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    @Operation(summary = "Retrieve all products", description = "Get a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductDto>> findAll(
            final @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            final @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        var products = this.productService.findAll(page, size);
        return ResponseEntity.ok(this.productMapper.toDto(products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a product by ID", description = "Get a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> findById(final @PathVariable("id") Long id) {
        var product = this.productService.findById(id);
        return ResponseEntity.ok(this.productMapper.toDto(product));
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created product"),
            @ApiResponse(responseCode = "400", description = "Invalid product details")
    })
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

    @PostMapping("/{productId}")
    public ResponseEntity<Void> uploadImage(
            final @PathVariable("productId") Long id,
            final @RequestParam("file") MultipartFile file
    ) {
        this.productService.uploadImage(id, file);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update an existing product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated product"),
            @ApiResponse(responseCode = "400", description = "Invalid product details or ID"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> update(
            final @PathVariable("id") Long id,
            final @Valid @RequestBody ProductDto productDto,
            final UriComponentsBuilder uriComponentsBuilder) {
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
    @Operation(summary = "Delete a product by ID", description = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted product"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
