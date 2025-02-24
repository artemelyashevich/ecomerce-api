package com.elyashevich.product.api.client;

import org.springframework.web.multipart.MultipartFile;

public interface ImageRestClient {

    public String uploadFile(final MultipartFile file);
}
