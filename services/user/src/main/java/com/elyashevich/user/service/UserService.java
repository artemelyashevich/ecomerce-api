package com.elyashevich.user.service;

import com.elyashevich.user.domain.entity.User;

import java.util.List;
/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    User findById(final Long id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user with the specified username
     */
    User findByUsername(final String username);

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    User create(final User user);

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param user the updated user information
     * @return the updated user
     */
    User update(final Long id, final User user);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    void delete(final Long id);

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the specified email
     */
    User findByEmail(final String email);
}

