package com.elyashevich.client.config;

import com.elyashevich.client.rest.CartRestClient;
import com.elyashevich.client.rest.CategoryRestClient;
import com.elyashevich.client.rest.ProductRestClient;
import com.elyashevich.client.rest.UserRestClient;
import com.elyashevich.client.rest.impl.CartRestClientImpl;
import com.elyashevich.client.rest.impl.CategoryRestClientImpl;
import com.elyashevich.client.rest.impl.ProductRestClientImpl;
import com.elyashevich.client.rest.impl.UserRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${manager.service.catalogue.uri:http://localhost:8091/api/v1%s}")
    private String baseUri;

    @Bean
    public ProductRestClient productRestClient() {
        return new ProductRestClientImpl(RestClient.builder()
                .baseUrl(this.baseUri.formatted("/products"))
                .build());
    }

    @Bean
    public CartRestClient cartRestClient() {
        return new CartRestClientImpl(RestClient.builder()
                .baseUrl(this.baseUri.formatted("/carts"))
                .build());
    }

    @Bean
    public UserRestClient userRestClient() {
        return new UserRestClientImpl(RestClient.builder()
                .baseUrl(this.baseUri.formatted("/users"))
                .build());
    }

    @Bean
    public CategoryRestClient categoryRestClient() {
        return new CategoryRestClientImpl(RestClient.builder()
                .baseUrl(this.baseUri.formatted("/categories"))
                .build());
    }
}