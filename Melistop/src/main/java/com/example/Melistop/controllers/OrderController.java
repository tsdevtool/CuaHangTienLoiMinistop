package com.example.Melistop.controllers;

import com.example.Melistop.models.Order;
import com.example.Melistop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getOrders(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Model model) {
        List<Order> orders;
        if (date == null) {
            orders = orderService.getAllOrders(); // Lấy tất cả đơn hàng nếu không có tham số date
        } else {
            orders = orderService.getOrdersByDate(date);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("date", date);
        return "oder/orderList";
    }

    @GetMapping("/orders/{id}")
    public String getOrderById(@PathVariable("id") Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "oder/orderDetail";
        } else {
            // Handle order not found
            return "redirect:/orders";
        }
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "oder/orderEdit";
        } else {
            // Handle order not found
            return "redirect:/orders";
        }
    }

    @PostMapping("/orders/edit/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
