package com.elyashevich.auth.service.impl;

import com.elyashevich.auth.api.client.UserRestClient;
import com.elyashevich.auth.api.dto.LoginDto;
import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.domain.Role;
import com.elyashevich.auth.domain.UserEntity;
import com.elyashevich.auth.exception.PasswordMismatchException;
import com.elyashevich.auth.service.AuthService;
import com.elyashevich.auth.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRestClient userRestClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(final LoginDto loginEntity) {
        var candidate = this.userRestClient.findUserByEmail(loginEntity.getEmail());

        if (!this.passwordEncoder.matches(loginEntity.getPassword(), candidate.getPassword())) {
            throw new PasswordMismatchException("Password does not match");
        }

        return TokenUtil.generateToken(convertToUserDetails(candidate));
    }

    @Override
    public String register(final RegisterDto registerEntity) {
        registerEntity.setPassword(this.passwordEncoder.encode(registerEntity.getPassword()));
        var candidate = this.userRestClient.saveUser(registerEntity);

        return TokenUtil.generateToken(convertToUserDetails(candidate));
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
}
