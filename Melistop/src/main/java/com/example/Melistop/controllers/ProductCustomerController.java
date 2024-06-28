package com.example.Melistop.controllers;

import com.example.Melistop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ProductCustomerController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String showProductListForCustomer(Model model){
        model.addAttribute("productList", productService.getAllProducts());
        return "/item/ProductList";

    }
}
