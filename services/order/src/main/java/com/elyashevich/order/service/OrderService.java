package com.elyashevich.order.service;

import com.elyashevich.order.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    Order findById(final Long id);

    Order create(final Order order);

    Order update(final Long id, Order order);

    void delete(final Long id);

    void complete(final Long orderId);
}
