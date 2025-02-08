package com.elyashevich.order.service;

import com.elyashevich.order.domain.Order;

import java.util.List;
/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    List<Order> findAll();

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID
     */
    Order findById(final Long id);

    /**
     * Creates a new order.
     *
     * @param order the order to create
     * @return the created order
     */
    Order create(final Order order);

    /**
     * Updates an existing order.
     *
     * @param id the ID of the order to update
     * @param order the updated order information
     * @return the updated order
     */
    Order update(final Long id, Order order);

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     */
    void delete(final Long id);

    /**
     * Completes an order by its ID.
     *
     * @param orderId the ID of the order to complete
     */
    void complete(final Long orderId);
}
