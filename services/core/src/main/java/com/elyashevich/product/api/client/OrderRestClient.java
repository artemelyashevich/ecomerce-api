package com.elyashevich.product.api.client;

import com.elyashevich.product.api.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderRestClient {

    @PostMapping("/api/v1/orders")
    void placeOrder(final @RequestBody OrderDto order);
}
