package com.elyashevich.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {

    @Value("${application.rest.orderServiceUri}:http://localhost:8094/api/v1/orders")
    private String baseUri;
}
