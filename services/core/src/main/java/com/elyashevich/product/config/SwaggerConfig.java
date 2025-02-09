package com.elyashevich.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String CORE_SERVICE_TITLE = "Core Service API";
    private static final String CORE_SERVICE_DESCRIPTION = "API documentation for the Core Service";
    private static final String CORE_SERVICE_VERSION = "1.0";

    @Value("${application.open-api.email:example}")
    private String email;

    @Value("${application.open-api.server:http://localhost:8222}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url(this.serverUrl)
                        )
                )
                .info(
                        new Info()
                                .title(CORE_SERVICE_TITLE)
                                .description(CORE_SERVICE_DESCRIPTION)
                                .version(CORE_SERVICE_VERSION)
                                .contact(new Contact().email(this.email))
                );
    }
}
