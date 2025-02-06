package com.elyashevich.order.api.mapper;

import com.elyashevich.order.api.dto.OrderDto;
import com.elyashevich.order.domain.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(final Order order);

    Order toEntity(final OrderDto orderDto);

    List<OrderDto> toDtoList(final List<Order> orders);
}
