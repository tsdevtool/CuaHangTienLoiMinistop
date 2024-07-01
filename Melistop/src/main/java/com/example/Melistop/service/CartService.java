package com.example.Melistop.service;

import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import com.example.Melistop.repository.CartItemRepository;
import com.example.Melistop.repository.ProductRepository;
import jakarta.transaction.Transactional;
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
    public void addToCart(Product product, int quantity, User user){
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(cartItem == null){
            cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            product.setQuantity(product.getQuantity() - quantity);
        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            product.setQuantity(product.getQuantity() - quantity);
        }
        cartItemRepository.save(cartItem);
    }

    //Lay tat ca san pham theo nguoi dung
    public List<CartItem> getCartItemsForUser(User user){
        return cartItemRepository.findByUser(user);
    }

//    Xoa san pham trong cart
    @Transactional
    public void removeProductInCartOfUser(Long cartId, User user){
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartId);
        if(cartItemOptional.isPresent()){
            CartItem cartItem = cartItemOptional.get();
            if(cartItem.getUser().equals(user)){
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() + cartItem.getQuantity());
                cartItemRepository.deleteByIdAndUser(cartId,user);
            }else{
                throw  new IllegalArgumentException("Nguoi dung khong khop nhau");
            }
        }else{
            throw new IllegalArgumentException("Không tìm thấy đơn hàng có id" + cartId);
        }

    }

    //    Xoa san pham trong cart
    @Transactional
    public void reduceProductInCartOfUser(Long cartId, User user, int quantity){
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartId);
        if(cartItemOptional.isPresent()){
            CartItem cartItem = cartItemOptional.get();
            if(cartItem.getUser().equals(user)){
                Product product = cartItem.getProduct();
                product.setQuantity(product.getQuantity() - quantity);
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
            }else{
                throw  new IllegalArgumentException("Nguoi dung khong khop nhau");
            }
        }else{
            throw new IllegalArgumentException("Không tìm thấy đơn hàng có id" + cartId);
        }

    }

    //Xoa toan bo san pham trong gio hang
    public void clearCart(){
        cartItems.clear();
    }
}