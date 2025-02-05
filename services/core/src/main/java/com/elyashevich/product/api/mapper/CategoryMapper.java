package com.elyashevich.product.api.mapper;

import com.elyashevich.product.api.dto.CategoryDto;
import com.elyashevich.product.domain.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CategoryMapper extends Mappable<Category, CategoryDto> {
}
