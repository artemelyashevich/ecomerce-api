package com.elyashevich.product.api.mapper;

import com.elyashevich.product.api.dto.CartDto;
import com.elyashevich.product.domain.entity.Cart;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface CartMapper extends Mappable<Cart, CartDto> {
}
