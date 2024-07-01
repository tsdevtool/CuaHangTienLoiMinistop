package com.example.Melistop.service;

import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.repository.CartItemRepository;
import com.example.Melistop.repository.ProductRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
@Service
@SessionScope //Quản lý bean trong session http
public class CartService{
    private List<CartItem> cartItems = new ArrayList<>();

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;

    //Them san pham vao gio hang
    public void addToCart (Product product, int quantity, User user){
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(cartItem == null){
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItemRepository.save(cartItem);
    }
}
