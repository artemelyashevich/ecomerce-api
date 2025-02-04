package com.elyashevich.product.api.controller;

import com.elyashevich.product.api.dto.CartDto;
import com.elyashevich.product.api.mapper.CartMapper;
import com.elyashevich.product.service.CartService;
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
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartDto>> findAllByUserId(final @PathVariable("userId") Long userId) {
        var carts = this.cartService.finaAllByUserId(userId);

        return ResponseEntity.ok(this.cartMapper.toDto(carts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> findById(final @PathVariable("id") Long id) {
        var cart = this.cartService.findById(id);

        return ResponseEntity.ok(this.cartMapper.toDto(cart));
    }

    @PostMapping
    public ResponseEntity<CartDto> create(
            final @Valid @RequestBody CartDto cartDto,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
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
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.cartService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/{userId}")
    public ResponseEntity<Void> deleteAll(final @PathVariable("userId") Long userId) {
        this.cartService.deleteAllByUserId(userId);

        return ResponseEntity.noContent().build();
    }
}
