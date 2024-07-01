package com.example.Melistop.controllers;

import com.example.Melistop.models.Order;
import com.example.Melistop.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private OrderManagementService orderManagementService;

    @GetMapping
    public String admin() {
        return "layoutemployee";
    }

    @GetMapping("/orders/list")
    public String listEmployeeOrders(Model model) {
        model.addAttribute("orders", orderManagementService.findDeliveredOrders());
        return "employee/orders/list";
    }

    @GetMapping("/orders/pending")
    public String listPendingOrders(Model model) {
        model.addAttribute("orders", orderManagementService.findPendingOrders());
        return "employee/orders/pending-list";
    }

    @PostMapping("/orders/status/{id}")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("source") String source) {
        Order order = orderManagementService.findOrderById(id).orElse(null);
        if (order != null) {
            if ("list".equals(source)) {
                // Chuyển trạng thái thành false khi tick từ danh sách hoàn tất
                order.setStatus(false);
            } else if ("pending".equals(source)) {
                // Chuyển trạng thái thành true khi tick từ danh sách chờ
                order.setStatus(true);
            }
            orderManagementService.updateOrder(id, order);
        }
        return "redirect:/employee/orders/" + source;
    }



    @GetMapping("/orders/detail/{id}")
    public String viewOrderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderManagementService.findOrderById(id).orElse(null);
        model.addAttribute("order", order);
        return "employee/orders/detail";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderManagementService.findOrderById(id).orElse(null);
        model.addAttribute("order", order);
        return "employee/orders/edit";
    }

    @PostMapping("/orders/edit/{id}")
    public String editOrder(@PathVariable("id") Long id,
                            @ModelAttribute("order") Order order) {
        orderManagementService.updateOrder(id, order);
        return "redirect:/employee/orders";
    }

}
