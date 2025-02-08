package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.CartDto;
import com.elyashevich.product.api.mapper.CartMapper;
import com.elyashevich.product.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/carts")
@Tag(name = "Carts", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
public final class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/{userId}")
    @Operation(summary = "Find all carts by user ID", description = "Retrieve all carts associated with a specific user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of carts"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<CartDto>> findAllByUserId(final @PathVariable("userId") Long userId) {
        var carts = this.cartService.finaAllByUserId(userId);
        return ResponseEntity.ok(this.cartMapper.toDto(carts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find cart by ID", description = "Retrieve a cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
            @ApiResponse(responseCode = "400", description = "Invalid cart ID"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<CartDto> findById(final @PathVariable("id") Long id) {
        var cart = this.cartService.findById(id);
        return ResponseEntity.ok(this.cartMapper.toDto(cart));
    }

    @PostMapping
    @Operation(summary = "Create a new cart", description = "Create a new cart with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created cart"),
            @ApiResponse(responseCode = "400", description = "Invalid cart details")
    })
    public ResponseEntity<CartDto> create(
            final @Valid @RequestBody CartDto cartDto,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.cartMapper.toEntity(cartDto);
        var cart = this.cartService.create(candidate);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/carts/{cartId}")
                                .build(Map.of("cartId", cart.getId()))
                )
                .body(this.cartMapper.toDto(cart));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cart by ID", description = "Delete a cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted cart"),
            @ApiResponse(responseCode = "400", description = "Invalid cart ID"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/{userId}")
    @Operation(summary = "Delete all carts by user ID", description = "Delete all carts associated with a specific user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all carts"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteAll(final @PathVariable("userId") Long userId) {
        this.cartService.deleteAllByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
