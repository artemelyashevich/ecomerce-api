package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.CategoryDto;
import com.elyashevich.product.api.mapper.CategoryMapper;
import com.elyashevich.product.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        var categories = this.categoryService.findAll();

        return ResponseEntity.ok(this.categoryMapper.toDto(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(final @PathVariable("id") Long id) {
        var category = this.categoryService.findById(id);

        return ResponseEntity.ok(this.categoryMapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(
            final @Valid @RequestBody CategoryDto categoryDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
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
    public ResponseEntity<CategoryDto> update(
            final @PathVariable("id") Long id,
            final @Valid @RequestBody CategoryDto categoryDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
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
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}