package com.elyashevich.auth.api.client;

import com.elyashevich.auth.api.dto.RegisterDto;
import com.elyashevich.auth.domain.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserRestClient {

    @GetMapping("/api/v1/users/{email}")
    UserEntity findByEmail(final @PathVariable("email") String email);

    @PostMapping("/api/v1/users")
    UserEntity save(final @RequestBody RegisterDto user);
}
