package com.elyashevich.auth.service.impl;

import com.elyashevich.auth.api.client.UserRestClient;
import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.api.dto.ResetPasswordDto;
import com.elyashevich.auth.api.dto.JwtResponse;
import com.elyashevich.auth.domain.RefreshToken;
import com.elyashevich.auth.domain.Role;
import com.elyashevich.auth.domain.UserEntity;
import com.elyashevich.auth.exception.PasswordMismatchException;
import com.elyashevich.auth.service.AuthService;
import com.elyashevich.auth.service.RefreshTokenService;
import com.elyashevich.auth.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.elyashevich.auth.util.TokenConstantUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRestClient userRestClient;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse login(final LoginDto loginEntity) {
        var candidate = this.userRestClient.findUserByEmail(loginEntity.getEmail());

        if (!this.passwordEncoder.matches(loginEntity.getPassword(), candidate.getPassword())) {
            throw new PasswordMismatchException("Password does not match");
        }

        return this.authenticate(convertToUserDetails(candidate));
    }

    @Override
    public JwtResponse register(final RegisterDto registerEntity) {
        registerEntity.setPassword(this.passwordEncoder.encode(registerEntity.getPassword()));
        var candidate = this.userRestClient.saveUser(registerEntity);

        return this.authenticate(convertToUserDetails(candidate));
    }

    @Override
    public JwtResponse refresh(final String token) {
        var email = TokenUtil.extractEmailClaims(token);
        var user = this.userRestClient.findUserByEmail(email);
        return new JwtResponse(TokenUtil.generateToken(convertToUserDetails(user), ACCESS_TOKEN_EXPIRES_TIME), token);
    }

    // TODO
    @Override
    public void resetPassword(final ResetPasswordDto resetPasswordDto, final String email) {

    }

    @Override
    public String verify(final String token) {
        return TokenUtil.extractEmailClaims(token);
    }

    private static UserDetails convertToUserDetails(final UserEntity userEntity) {
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream()
                        .map(Role::name)
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }

    private JwtResponse authenticate(final UserDetails user) {
        var accessToken = TokenUtil.generateToken(user, ACCESS_TOKEN_EXPIRES_TIME);
        var refreshToken = TokenUtil.generateToken(user, REFRESH_TOKEN_EXPIRES_TIME);
        this.refreshTokenService.save(RefreshToken.builder()
                        .email(user.getUsername())
                        .token(refreshToken)
                        .expires(REFRESH_TOKEN_EXPIRES_TIME)
                .build());
        return new JwtResponse(accessToken, refreshToken);
    }
}
