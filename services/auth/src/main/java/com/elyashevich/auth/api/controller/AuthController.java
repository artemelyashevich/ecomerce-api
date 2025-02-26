package com.elyashevich.auth.api.controller;

import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.api.dto.VerifyTokenRequest;
import com.elyashevich.auth.api.dto.VerifyTokenResponse;
import com.elyashevich.auth.api.dto.JwtResponse;
import com.elyashevich.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication related endpoints")
@RequiredArgsConstructor
public final class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticate a user with the provided login details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<JwtResponse> login(final @Valid @RequestBody LoginDto loginEntity) {
        var jwtResponse = this.authService.login(loginEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jwtResponse);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user with the provided registration details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<JwtResponse> register(final @Valid @RequestBody RegisterDto registerEntity) {
        var tokenResponse = this.authService.register(registerEntity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tokenResponse);
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify a user token", description = "Verify a user's token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully verified"),
            @ApiResponse(responseCode = "400", description = "Invalid token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<VerifyTokenResponse> verify(final @Valid @RequestBody VerifyTokenRequest verifyTokenRequest) {
        var email = this.authService.verify(verifyTokenRequest.getToken());
        return ResponseEntity
                .accepted()
                .body(new VerifyTokenResponse(email));
    }
}
