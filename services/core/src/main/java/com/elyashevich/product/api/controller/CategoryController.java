package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.CategoryDto;
import com.elyashevich.product.api.mapper.CategoryMapper;
import com.elyashevich.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @Operation(summary = "Retrieve all categories", description = "Get a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryDto>> findAll() {
        var categories = this.categoryService.findAll();
        return ResponseEntity.ok(this.categoryMapper.toDto(categories));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a category by ID", description = "Get a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            @ApiResponse(responseCode = "400", description = "Invalid category ID"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDto> findById(final @PathVariable("id") Long id) {
        var category = this.categoryService.findById(id);
        return ResponseEntity.ok(this.categoryMapper.toDto(category));
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created category"),
            @ApiResponse(responseCode = "400", description = "Invalid category details")
    })
    public ResponseEntity<CategoryDto> create(
            final @Valid @RequestBody CategoryDto categoryDto,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.categoryMapper.toEntity(categoryDto);
        var newCategory = this.categoryService.create(candidate);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/categories/{categoryId}")
                                .build(Map.of("categoryId", newCategory.getId()))
                )
                .body(this.categoryMapper.toDto(newCategory));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Update an existing category with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated category"),
            @ApiResponse(responseCode = "400", description = "Invalid category details or ID"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDto> update(
            final @PathVariable("id") Long id,
            final @Valid @RequestBody CategoryDto categoryDto,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.categoryMapper.toEntity(categoryDto);
        var updatedCategory = this.categoryService.update(id, candidate);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/categories/{categoryId}")
                                .build(Map.of("categoryId", updatedCategory.getId()))
                )
                .body(this.categoryMapper.toDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID", description = "Delete a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted category"),
            @ApiResponse(responseCode = "400", description = "Invalid category ID"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
