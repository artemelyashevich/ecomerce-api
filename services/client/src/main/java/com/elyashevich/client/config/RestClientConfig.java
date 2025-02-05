package com.elyashevich.client.config;

import com.elyashevich.client.rest.ProductRestClient;
import com.elyashevich.client.rest.impl.ProductRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public ProductRestClient productRestClient(
            @Value("${manager.service.catalogue.uri:http://localhost:8091}") String baseUri
    ) {
        return new ProductRestClientImpl(RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build());
    }
}