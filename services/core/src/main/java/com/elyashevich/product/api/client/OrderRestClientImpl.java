package com.elyashevich.product.api.client;

import com.elyashevich.product.api.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class OrderRestClientImpl implements OrderRestClient {

    private final RestClient restClient;

    @Override
    public void placeOrder(OrderDto order) {

    }
}
