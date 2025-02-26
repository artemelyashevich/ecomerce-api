package com.elyashevich.product.api.client.impl;

import com.elyashevich.product.api.client.ImageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class ImageRestClientImpl implements ImageRestClient {

    private final RestClient restClient;

    @Override
    public String uploadFile(final MultipartFile file) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        var body = new LinkedMultiValueMap<String, Object>();
        body.add("file", new HttpEntity<>(file.getResource(), httpHeaders));

        return this.restClient
                .post()
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(String.class);
    }

}
