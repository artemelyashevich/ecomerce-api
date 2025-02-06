package com.elyashevich.auth.api.controller;

import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.api.dto.TokenResponse;
import com.elyashevich.auth.api.dto.VerifyTokenRequest;
import com.elyashevich.auth.api.dto.VerifyTokenResponse;
import com.elyashevich.auth.api.dto.VerifyTokenResponse;
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
    public ResponseEntity<TokenResponse> login(final @Valid @RequestBody LoginDto loginEntity) {
        var tokenResponse = authService.login(loginEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new TokenResponse(tokenResponse)));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(final @Valid @RequestBody RegisterDto registerEntity) {
        var tokenResponse = authService.register(registerEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((new TokenResponse(tokenResponse)));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyTokenResponse> verify(final @Valid @RequestBody VerifyTokenRequest verifyTokenRequest) {
        var email = this.authService.verify(verifyTokenRequest.getToken());
        return ResponseEntity
                .accepted()
                .body(new VerifyTokenResponse(email));
    }
}
