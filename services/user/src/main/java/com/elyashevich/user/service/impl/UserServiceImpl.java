package com.elyashevich.user.service.impl;

import com.elyashevich.user.domain.entity.Role;
import com.elyashevich.user.domain.entity.User;
import com.elyashevich.user.exception.ResourceNotFoundException;
import com.elyashevich.user.repository.UserRepository;
import com.elyashevich.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_WITH_ID_WAS_NOT_FOUND_TEMPLATE = "User with id '%d' was not found";
    public static final String USER_WITH_USERNAME_WAS_NOT_FOUND_TEMPLATE = "User with username '%s' was not found";
    public static final String USER_WITH_EMAIL_S_WAS_NOT_FOUND = "User with email '%s' was not found";
    public static final String USER_WITH_EMAIL_WAS_NOT_FOUND_TEMPLATE = USER_WITH_EMAIL_S_WAS_NOT_FOUND;

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.debug("Attempting to find all users");

        var users = userRepository.findAll();

        log.info("Found {} users", users.size());
        return users;
    }

    @Override
    public User findById(final Long id) {
        log.debug("Attempting to find user with id {}", id);

        var user = userRepository.findById(id).orElseThrow(
                ()-> {
                    var message = USER_WITH_ID_WAS_NOT_FOUND_TEMPLATE.formatted(id);

                    log.warn(message);

                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found user with id {}", id);
        return user;
    }

    @Override
    public User findByUsername(final String username) {
        log.debug("Attempting to find user with name {}", username);

        var user = userRepository.findByUsername(username).orElseThrow(
                ()-> {
                    var message = USER_WITH_USERNAME_WAS_NOT_FOUND_TEMPLATE.formatted(username);

                    log.warn(message);

                    return new ResourceNotFoundException(message);
                }
        );

        log.info("Found user with name {}", username);
        return user;
    }

    @Override
    public User create(final User user) {
        log.debug("Attempting to create user {}", user.getId());

        user.addRole(Role.ROLE_USER);
        var newUser = this.userRepository.save(user);

        log.info("Created new user {}", newUser.getId());
        return user;
    }

    @Transactional
    @Override
    public User update(final Long id, final User user) {
        log.debug("Attempting to update user with id {}", id);

        var oldUser = this.findById(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setRoles(user.getRoles());

        var newUser = this.userRepository.save(oldUser);

        log.info("Updated user with id {}", id);
        return newUser;
    }

    @Override
    public void delete(final Long id) {
        log.debug("Attempting to delete user with id {}", id);

        var oldUser = this.findById(id);
        this.userRepository.delete(oldUser);

        log.info("Deleted user with id {}", id);
    }

    @Override
    public User findByEmail(final String email) {
        log.debug("Attempting to find user with email {}", email);
        var user = this.userRepository.findByEmail(email).orElseThrow(
                ()-> {
                    var message = USER_WITH_EMAIL_WAS_NOT_FOUND_TEMPLATE.formatted(email);

                    log.warn(message);
                    return new ResourceNotFoundException(message);
                }
        );
        return user;
    }
}
