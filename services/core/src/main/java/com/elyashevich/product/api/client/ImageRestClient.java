package com.elyashevich.product.api.client;

import com.elyashevich.product.api.dto.ImageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "image-service")
public interface ImageRestClient {

    @GetMapping("/api/v1/images/{id}")
    ImageDto findById(final @PathVariable("id") String email);

    @PostMapping(value = "/api/v1/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageDto upload(final @RequestParam("file") MultipartFile file);
}
