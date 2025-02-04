package com.elyashevich.client.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/categories")
    public String createCategory() {
        return "admin/categories";
    }

    @GetMapping("/users")
    public String createUser() {
        return "admin/users";
    }

    @GetMapping("/products/create")
    public String createProduct() {
        return "admin/create-product";
    }

    @GetMapping("/categories/edit/{id}")
    public String editProduct() {
        return "admin/edit-category";
    }
}