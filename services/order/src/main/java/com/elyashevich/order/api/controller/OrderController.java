package com.elyashevich.order.api.controller;

import com.elyashevich.order.api.dto.OrderDto;
import com.elyashevich.order.api.mapper.OrderMapper;
import com.elyashevich.order.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    @Operation(summary = "Retrieve all orders", description = "Get a list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<OrderDto>> findAll() {
        var orders = this.orderService.findAll();
        return ResponseEntity.ok(this.orderMapper.toDtoList(orders));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve an order by ID", description = "Get an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order"),
            @ApiResponse(responseCode = "400", description = "Invalid order ID"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> findById(final @PathVariable("id") Long id) {
        var order = this.orderService.findById(id);
        return ResponseEntity.ok(this.orderMapper.toDto(order));
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created order"),
            @ApiResponse(responseCode = "400", description = "Invalid order details")
    })
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
    @Operation(summary = "Update an order", description = "Update an existing order with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated order"),
            @ApiResponse(responseCode = "400", description = "Invalid order details or ID"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderDto> update(
            final @PathVariable("id") Long id,
            final @Valid @RequestBody OrderDto orderDto,
            final UriComponentsBuilder uriComponentsBuilder) {
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
    @Operation(summary = "Delete an order by ID", description = "Delete an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order"),
            @ApiResponse(responseCode = "400", description = "Invalid order ID"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
