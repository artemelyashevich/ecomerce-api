package com.elyashevich.auth.config;

import com.elyashevich.auth.api.client.UserRestClient;
import com.elyashevich.auth.api.client.UserRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${application.userService.baseUri:http://localhost:8092/api/v1/users}")
    private String baseUri;

    @Bean
    UserRestClient userRestClient() {
        return new UserRestClientImpl(
                RestClient.builder()
                        .baseUrl(baseUri)
                        .build()
        );
    }
}
