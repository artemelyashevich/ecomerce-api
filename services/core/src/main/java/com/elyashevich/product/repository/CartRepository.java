package com.elyashevich.product.repository;

import com.elyashevich.product.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserId(final Long userId);

    void deleteAllByUserId(final Long userId);
}
