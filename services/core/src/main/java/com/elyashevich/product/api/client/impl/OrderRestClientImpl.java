package com.elyashevich.product.api.client.impl;

import com.elyashevich.product.api.client.OrderRestClient;
import com.elyashevich.product.api.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class OrderRestClientImpl implements OrderRestClient {

    private final RestClient restClient;

    @Override
    public void placeOrder(final OrderDto order) {

    }
}
