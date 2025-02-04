package com.elyashevich.client.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public String getProductsPage() {
        return "product/products";
    }

    @GetMapping("/{id}")
    public String product(final @PathVariable("id") Long id) {
        return "product/product";
    }
}
