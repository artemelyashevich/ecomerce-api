package com.elyashevich.order.service.impl;

import com.elyashevich.order.domain.EventType;
import com.elyashevich.order.domain.Order;
import com.elyashevich.order.domain.OrderEvent;
import com.elyashevich.order.domain.OrderStatus;
import com.elyashevich.order.exception.ResourceNotFoundException;
import com.elyashevich.order.kafka.producer.OrderProducer;
import com.elyashevich.order.repository.OrderRepository;
import com.elyashevich.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_WITH_ID_WAS_NOT_FOUND_TEMPLATE = "Order with id '%s' was not found";
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Override
    public List<Order> findAll() {
        log.debug("Attempting find all products");

        var products = this.orderRepository.findAll();

        log.info("Found {} products", products.size());
        return products;
    }

    @Override
    public Order findById(final Long id) {
        log.debug("Attempting find product by id: {}", id);

        var order = this.orderRepository.findById(id).orElseThrow(
                () -> {
                    var message = ORDER_WITH_ID_WAS_NOT_FOUND_TEMPLATE.formatted(id);

                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found order: {}", order);
        return order;
    }

    @Override
    public Order create(final Order order) {
        log.debug("Attempting create order: {}", order);

        order.setStatus(OrderStatus.PENDING);
        var newOrder = this.orderRepository.save(order);
        this.orderProducer.sendOrderEvent(OrderEvent.builder()
                .orderId(newOrder.getId())
                .status(order.getStatus())
                .event(EventType.CREATE)
                .build());

        log.info("Created order: {}", newOrder);
        return newOrder;
    }

    @Transactional
    @Override
    public Order update(final Long id, final Order order) {
        log.debug("Attempting update order with id: {}", id);

        var oldOrder = this.findById(id);

        oldOrder.setAmount(order.getAmount());
        var updatedOrder = this.orderRepository.save(oldOrder);
        this.orderProducer.sendOrderEvent(OrderEvent.builder()
                .orderId(updatedOrder.getId())
                .status(order.getStatus())
                .event(EventType.UPDATE)
                .build());

        log.info("Updated order: {}", updatedOrder);
        return updatedOrder;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        log.debug("Attempting delete order with id: {}", id);

        var order = this.findById(id);
        this.orderRepository.delete(order);
        this.orderProducer.sendOrderEvent(OrderEvent.builder()
                .orderId(id)
                .status(OrderStatus.REJECTED)
                .event(EventType.DELETE)
                .build());

        log.info("Deleted order: {}", order);
    }

    @Transactional
    @Override
    public void complete(final Long orderId) {
        log.info("Completing order with id: {}", orderId);

        var order = this.findById(orderId);
        order.setStatus(OrderStatus.FULFILLED);
        this.orderRepository.save(order);

        log.info("Completed order: {}", order);
    }
}
