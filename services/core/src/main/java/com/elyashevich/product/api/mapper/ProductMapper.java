package com.elyashevich.product.api.mapper;

import com.elyashevich.product.api.dto.ProductDto;
import com.elyashevich.product.domain.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Mappable<Product, ProductDto> {
}
