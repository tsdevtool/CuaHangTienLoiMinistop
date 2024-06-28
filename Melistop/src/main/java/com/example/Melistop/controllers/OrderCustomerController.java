package com.example.Melistop.controllers;

import com.example.Melistop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderCustomerController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String showOrder(@RequestParam Long customerId, Model model){
        model.addAttribute("product");
        model.addAttribute("cart", orderService.getOrderDertailByCustomerId(customerId));
        return "user/Cart";
    }
    @PostMapping("/add")
    public String addProductToOrder(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam Long quantity, Model model){
        orderService.addProductToOrder(customerId, productId, quantity);
        model.addAttribute("message","Product added to order successfully");
        return "item/ProductList";
    }
}
