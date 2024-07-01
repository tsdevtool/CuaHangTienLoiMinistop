package com.example.Melistop.controllers;

import com.example.Melistop.models.Order;
import com.example.Melistop.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class OrderManagementController {
    @Autowired
    private OrderManagementService orderManagementService;

    @GetMapping
    public String listCompletedOrders(Model model) {
        model.addAttribute("orders", orderManagementService.findAllOrders());
        return "admin/orders/list";
    }

    @GetMapping("detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Long id, Model model) {
        Optional<Order> order = orderManagementService.findOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "admin/orders/detail";
        } else {
            return "redirect:/admin/orders";
        }
    }

}
