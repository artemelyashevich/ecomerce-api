package com.elyashevich.client.api.controller;

import com.elyashevich.client.domain.Category;
import com.elyashevich.client.domain.Product;
import com.elyashevich.client.rest.ProductRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRestClient productRestClient;

    @GetMapping
    public String getProductsPage(final Model model) {
        var products = this.productRestClient.findAll();

        model.addAttribute("products", products);
        return "product/products";
    }

    @GetMapping("/{id}")
    public String product(final @PathVariable("id") Long id, final Model model) {
        var product = this.productRestClient.findById(id);

        model.addAttribute("product", product);
        return "product/product";
    }

    @PostMapping
    public String addProduct(
            final @Validated @ModelAttribute Product product,
            final @ModelAttribute("categoryName") String categoryName
    ) {
        var category = new Category();
        category.setName(categoryName);
        product.setCategory(category);
        this.productRestClient.create(product);

        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(
            final @Validated @ModelAttribute Product product,
            final @ModelAttribute String categoryName,
            final @PathVariable("id") Long id
    ) {
        var category = new Category();
        category.setName(categoryName);

        product.setCategory(category);
        this.productRestClient.update(id, product);

        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(final @Validated @ModelAttribute Product product, final @PathVariable("id") Long id) {
        this.productRestClient.delete(id);

        return "redirect:/products";
    }
}
