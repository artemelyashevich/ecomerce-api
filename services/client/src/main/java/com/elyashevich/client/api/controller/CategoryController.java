package com.elyashevich.client.api.controller;

import com.elyashevich.client.domain.Category;
import com.elyashevich.client.rest.CategoryRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRestClient categoryRestClient;

    @PostMapping
    public String addCategory(final @ModelAttribute Category category) {
        this.categoryRestClient.create(category);

        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(final @PathVariable("id") Long id) {
        this.categoryRestClient.delete(id);

        return "redirect:/admin/categories";
    }
}
