package com.elyashevich.auth.api.controller;

import com.elyashevich.auth.domain.LoginEntity;
import com.elyashevich.auth.domain.RegisterEntity;
import com.elyashevich.auth.domain.TokenResponse;
import com.elyashevich.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(final @Valid @RequestBody LoginEntity loginEntity) {
        var tokenResponse = authService.login(loginEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new TokenResponse(tokenResponse)));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(final @Valid @RequestBody RegisterEntity registerEntity) {
        var tokenResponse = authService.register(registerEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new TokenResponse(tokenResponse)));
    }
}
