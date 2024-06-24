package com.example.Melistop.service;

import com.example.Melistop.models.Cart;
import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.Customer;
import com.example.Melistop.models.Product;
import com.example.Melistop.repository.CartItemRepository;
import com.example.Melistop.repository.CartRepository;
import com.example.Melistop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    public Cart getCartByCustomer(Customer customer){
        return cartRepository.findByCustomer(customer);
    }

    public void addProductToCart(Customer customer, Long productId, int quantity){
        Cart cart = cartRepository.findByCustomer(customer);
        if (cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }

        Product product  = productRepository.findById(productId).orElse(null);
        if(product!=null){
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }
}
