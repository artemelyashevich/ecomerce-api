package com.elyashevich.product.api.mapper;

import java.util.List;

public interface Mappable<E, D> {

    E toEntity(final D d);

    D toDto(final E e);

    List<D> toDto(final List<E> e);
}
