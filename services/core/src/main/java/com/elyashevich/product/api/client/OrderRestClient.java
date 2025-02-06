package com.elyashevich.product.api.client;

import com.elyashevich.product.api.dto.OrderDto;

public interface OrderRestClient {

    void placeOrder(final OrderDto order);
}
