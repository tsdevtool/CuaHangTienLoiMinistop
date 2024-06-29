package com.example.Melistop.controllers;

import com.example.NguyenThanhSieu_9116.model.CartItem;
import com.example.NguyenThanhSieu_9116.service.CartService;
import com.example.NguyenThanhSieu_9116.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout(){
        return "/cart/checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(String customerName,String numberPhone, String email, String addressShip, String description, Model model){
        List<CartItem> cartItems = cartService.getCartItems();
        if(cartItems.isEmpty()){
            model.addAttribute("message","Your cart is empty");
            return "redirect:/cart/checkout";
        }
        orderService.createOrder(customerName, numberPhone, email, addressShip, description,cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model){
        model.addAttribute("message", "Your order has been successfully placed");
        return "cart/order-confirmation";
    }
}
