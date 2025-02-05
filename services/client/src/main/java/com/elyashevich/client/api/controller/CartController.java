package com.elyashevich.client.api.controller;

import com.elyashevich.client.rest.CartRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRestClient cartRestClient;

    @GetMapping
    public String getCart() {
        return "cart/cart";
    }
}