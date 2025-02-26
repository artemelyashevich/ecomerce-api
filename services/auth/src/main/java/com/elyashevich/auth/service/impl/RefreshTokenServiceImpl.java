package com.elyashevich.auth.service.impl;

import com.elyashevich.auth.domain.RefreshToken;
import com.elyashevich.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final RefreshToken refreshToken) {
        this.redisTemplate.opsForValue().set(
                refreshToken.getEmail(),
                refreshToken.getToken(),
                refreshToken.getExpires(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String findByUserEmail(final String userEmail) {
        return (String)this.redisTemplate.opsForValue().get(userEmail);
    }
}
