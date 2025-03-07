package com.elyashevich.gateway.filter;

import com.elyashevich.gateway.dto.VerifyRequest;
import com.elyashevich.gateway.dto.VerifyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class CustomAuthenticationFilter extends AbstractGatewayFilterFactory<CustomAuthenticationFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String MISSING_JWT_TOKEN = "Invalid or missing JWT token";

    private final WebClient webClient;

    public static class Config {

    }

    @Autowired
    public CustomAuthenticationFilter(final WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient.build();
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            if (!RouteValidator.isSecured.test(exchange.getRequest())) {
                log.info(exchange.getRequest().getURI().toString());
                return chain.filter(exchange);
            }
            var authorizationHeaders = exchange
                    .getRequest()
                    .getHeaders()
                    .getOrDefault(AUTHORIZATION_HEADER, Collections.emptyList());
            var jwt = authorizationHeaders.stream()
                    .filter(Objects::nonNull)
                    .filter(header -> header.trim().startsWith("Bearer "))
                    .map(header -> header.substring(7))
                    .findFirst()
                    .orElse(null);
            return jwt != null
                    ? validateToken(exchange, chain, jwt)
                    : handleInvalidAccess(HttpStatus.UNAUTHORIZED, exchange, MISSING_JWT_TOKEN);
        };
    }

    private Mono<Void> validateToken(
            final ServerWebExchange exchange,
            final GatewayFilterChain chain,
            final String token
    ) {
        return webClient.post()
                .uri("http://auth-service/api/v1/auth/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new VerifyRequest(token))
                .retrieve()
                .bodyToMono(VerifyResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getEmail() == null) {
                        return handleInvalidAccess(HttpStatus.UNAUTHORIZED, exchange, "Invalid token response");
                    }
                    var mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Email", response.getEmail())
                            .build();
                    if (RouteValidator.isAdminRequest.test(exchange.getRequest())) {
                        if (!response.getRoles().contains("ROLE_ADMIN")) {
                            return handleInvalidAccess(HttpStatus.FORBIDDEN, exchange, "Invalid role...");
                        }
                    }
                    var mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(e -> handleInvalidAccess(HttpStatus.UNAUTHORIZED, exchange, "Token validation failed"));
    }

    private Mono<Void> handleInvalidAccess(final HttpStatus status, final ServerWebExchange exchange, final String errorMessage) {
        var response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var res = Mono.just(response.bufferFactory().wrap(errorMessage.getBytes()));
        return response.writeWith(res);
    }
}