package com.example.Melistop.controllers;

import com.example.NguyenThanhSieu_9116.model.CartItem;
import com.example.NguyenThanhSieu_9116.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import  java.util.*;
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping
    public String showCart(Model model){
        model.addAttribute("cartItems", cartService.getCartItems());

        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return"/cart/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long productId, @RequestParam String action) {
        if ("increase".equals(action)) {
            cartService.updateQuantity(productId, 1);
        } else if ("decrease".equals(action)) {
            cartService.updateQuantity(productId, -1);
        }
        return "redirect:/cart";
    }



    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity){

        cartService.addToCard(productId, quantity);
        return "redirect:/cart";
    }
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId){
        cartService.removeFromCart(productId);
        return"redirect:/cart";
    }
    @GetMapping("/clear")
    public String clearCart(){
        cartService.clearCart();
        return "redirect:/cart";
    }
}