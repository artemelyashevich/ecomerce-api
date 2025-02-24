package com.elyashevich.product.config;

import com.elyashevich.product.api.client.ImageRestClient;
import com.elyashevich.product.api.client.OrderRestClient;
import com.elyashevich.product.api.client.impl.ImageRestClientImpl;
import com.elyashevich.product.api.client.impl.OrderRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${application.rest.orderServiceUri}:http://localhost:8094/api/v1/orders")
    private String baseOrderUri;

    @Value("${application.rest.orderServiceUri}:http://localhost:8099/api/v1/images")
    private String baseImageUri;

    @Bean
    public OrderRestClient orderRestClient(){
            return new OrderRestClientImpl(
                    RestClient.builder()
                            .baseUrl(this.baseOrderUri)
                            .build()
            );
    }

    @Bean
    public ImageRestClient imageRestClient(){
        return new ImageRestClientImpl(
                RestClient.builder()
                        .baseUrl(this.baseImageUri)
                        .build()
        );
    }
}
