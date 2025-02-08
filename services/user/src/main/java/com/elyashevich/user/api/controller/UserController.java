package com.elyashevich.user.api.controller;

import com.elyashevich.user.api.dto.UserDto;
import com.elyashevich.user.api.dto.UserRequestDto;
import com.elyashevich.user.api.dto.contract.Validate;
import com.elyashevich.user.api.mapper.UserMapper;
import com.elyashevich.user.domain.entity.User;
import com.elyashevich.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for managing users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Retrieve all users", description = "Get a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDto>> findAll() {
        var users = this.userService.findAll();
        return ResponseEntity.ok(this.userMapper.toUserDtoList(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a user by ID", description = "Get a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> findById(final @PathVariable("id") Long id) {
        var user = this.userService.findById(id);
        return ResponseEntity.ok(this.userMapper.toUserDto(user));
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Retrieve a user by username", description = "Get a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "400", description = "Invalid username"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> findByUsername(final @PathVariable("username") String username) {
        var user = this.userService.findByUsername(username);
        return ResponseEntity.ok(this.userMapper.toUserDto(user));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Retrieve a user by email", description = "Get a user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "400", description = "Invalid email"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> findByEmail(final @PathVariable("email") String email) {
        var user = this.userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user"),
            @ApiResponse(responseCode = "400", description = "Invalid user details")
    })
    public ResponseEntity<User> create(
            final @Validated(Validate.class) @RequestBody UserRequestDto user,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.userMapper.toUser(user);
        var newUser = this.userService.create(candidate);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/users/{userId}")
                                .build(Map.of("userId", newUser.getId()))
                )
                .body(newUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update an existing user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user"),
            @ApiResponse(responseCode = "400", description = "Invalid user details or ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> update(
            final @Validated(Validate.class) @RequestBody UserRequestDto user,
            final @PathVariable("id") Long id,
            final UriComponentsBuilder uriComponentsBuilder) {
        var candidate = this.userMapper.toUser(user);
        var newUser = this.userService.update(id, candidate);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/users/{userId}")
                                .build(Map.of("userId", newUser.getId()))
                )
                .body(this.userMapper.toUserDto(newUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by ID", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}