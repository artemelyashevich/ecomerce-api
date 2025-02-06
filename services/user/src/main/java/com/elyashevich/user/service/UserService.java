package com.elyashevich.user.service;

import com.elyashevich.user.domain.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(final Long id);

    User findByUsername(final String username);

    User create(final User user);

    User update(final Long id, final User user);

    void delete(final Long id);

    User findByEmail(final String email);
}
