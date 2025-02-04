package com.elyashevich.product.repository;

import com.elyashevich.product.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(final String name);
}
