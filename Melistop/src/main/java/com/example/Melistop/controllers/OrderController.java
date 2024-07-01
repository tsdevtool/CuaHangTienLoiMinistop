package com.example.Melistop.controllers;

import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.User;
import com.example.Melistop.service.CartService;
import com.example.Melistop.service.OrderService;
import com.example.Melistop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(){
        return "users/checkout";
    }

    @PostMapping("/submit-order")
    public String submitOrder(String addressShip, String receiveTime,String description,String delivery){
        // Lay thong tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(currentUsername);
        User user = userOpt.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + currentUsername));

        List<CartItem> cartItems = cartService.getCartItemsForUser(user);
        if(cartItems.isEmpty())
            return "redirect:/cart";
        orderService.createOrder(user, addressShip, receiveTime, description, delivery, cartItems);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model){
        model.addAttribute("message","Cảm ơn bạn đã đặt hàng cho chúng tôi");
        return "users/order-confirmation";
    }
}
