package com.elyashevich.user.api.controller;

import com.elyashevich.user.api.dto.UserDto;
import com.elyashevich.user.api.dto.UserRequestDto;
import com.elyashevich.user.api.dto.contract.Validate;
import com.elyashevich.user.api.mapper.UserMapper;
import com.elyashevich.user.service.UserService;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        var users = this.userService.findAll();

        return ResponseEntity.ok(this.userMapper.toUserDtoList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(final @PathVariable("id") Long id) {
        var user = this.userService.findById(id);

        return ResponseEntity.ok(this.userMapper.toUserDto(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findByUsername(final @PathVariable("username") String username) {
        var user = this.userService.findByUsername(username);

        return ResponseEntity.ok(this.userMapper.toUserDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(
            final @Validated(Validate.class) @RequestBody UserRequestDto user,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
        var candidate = this.userMapper.toUser(user);

        var newUser = this.userService.create(candidate);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .replacePath("/api/v1/users/{userId}")
                                .build(Map.of("userId", newUser.getId()))
                )
                .body(this.userMapper.toUserDto(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            final @Validated(Validate.class) @RequestBody UserRequestDto user,
            final @PathVariable("id") Long id,
            final UriComponentsBuilder uriComponentsBuilder
    ) {
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
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        this.userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
