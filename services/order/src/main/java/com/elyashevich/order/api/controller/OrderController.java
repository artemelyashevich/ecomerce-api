package com.elyashevich.order.api.controller;

import com.elyashevich.order.api.dto.OrderDto;
import com.elyashevich.order.api.mapper.OrderMapper;
import com.elyashevich.order.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll() {
        var orders = orderService.findAll();

        return ResponseEntity.ok(this.orderMapper.toDtoList(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(final @PathVariable("id") Long id) {
        var order = orderService.findById(id);

        return ResponseEntity.ok(this.orderMapper.toDto(order));
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(
            final @Valid @RequestBody OrderDto orderDto,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.orderMapper.toEntity(orderDto);

        var order = this.orderService.create(candidate);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/orders/{orderId}")
                                .build(Map.of("orderId", order.getId()))
                )
                .body(this.orderMapper.toDto(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(
            final @PathVariable("id") Long id,
            final @Valid OrderDto orderDto,
            final UriComponentsBuilder uriComponentsBuilder
            ) {
        var candidate = this.orderMapper.toEntity(orderDto);

        var order = this.orderService.update(id, candidate);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/orders/{orderId}")
                                .build(Map.of("orderId", order.getId()))
                )
                .body(this.orderMapper.toDto(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.orderService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
