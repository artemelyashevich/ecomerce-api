package com.elyashevich.product.repository;

import com.elyashevich.product.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(final String name);

    Optional<Category> findByName(final String name);
}
