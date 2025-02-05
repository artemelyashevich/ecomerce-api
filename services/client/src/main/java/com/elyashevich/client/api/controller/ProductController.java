package com.elyashevich.client.api.controller;

import com.elyashevich.client.rest.ProductRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRestClient productRestClient;

    @GetMapping
    public String getProductsPage(final Model model) {
        model.addAttribute("products", this.productRestClient.findAllProducts());
        return "product/products";
    }

    @GetMapping("/{id}")
    public String product(final @PathVariable("id") Long id) {
        return "product/product";
    }
}
