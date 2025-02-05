package com.elyashevich.client.api.controller;

import com.elyashevich.client.domain.Product;
import com.elyashevich.client.rest.CategoryRestClient;
import com.elyashevich.client.rest.ProductRestClient;
import com.elyashevich.client.rest.UserRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryRestClient categoryRestClient;
    private final UserRestClient userRestClient;
    private final ProductRestClient productRestClient;

    @GetMapping("/categories")
    public String createCategory(final Model model) {
        var categories = this.categoryRestClient.findAll();

        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @GetMapping("/users")
    public String createUser() {
        return "admin/users";
    }

    @GetMapping("/products/create")
    public String createProduct(final Model model) {
        var categories = this.categoryRestClient.findAll();
        model.addAttribute("categories", categories);

        return "admin/create-product";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(final Model model, final @PathVariable("id")Long id) {
        var categories = this.categoryRestClient.findAll();
        var product = this.productRestClient.findById(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/edit-product";
    }

}