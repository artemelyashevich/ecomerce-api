package com.elyashevich.product.api.client.impl;

import com.elyashevich.product.api.client.ImageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class ImageRestClientImpl implements ImageRestClient {

    private final RestClient restClient;

    @Override
    public String uploadFile(final MultipartFile file) {
        return this.restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("file", file)
                        .build())
                .body(file)
                .retrieve()
                .body(String.class);
    }
}
