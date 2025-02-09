package com.elyashevich.user.repository;

import com.elyashevich.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    Optional<User> findByEmail(final String email);
}
