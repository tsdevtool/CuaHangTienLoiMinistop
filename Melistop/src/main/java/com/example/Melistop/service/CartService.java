package com.example.Melistop.service;

import com.example.NguyenThanhSieu_9116.model.CartItem;
import com.example.NguyenThanhSieu_9116.model.Product;
import com.example.NguyenThanhSieu_9116.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems=new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;
    public void addToCard(Long productId, int quantity){
//        Product product=productRepository.findById(productId)
//                .orElseThrow(()->new IllegalArgumentException("Product not found:"+productId));
//        cartItems.add(new CartItem(product,quantity));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                // Nếu sản phẩm đã tồn tại, cộng dồn số lượng
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > product.getQuantity()) {
                    newQuantity = product.getQuantity(); // Đảm bảo không vượt quá số lượng tối đa
                }
                item.setQuantity(newQuantity);
                found = true;
                break;
            }
        }

        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm một mục mới vào giỏ hàng
        if (!found) {
            cartItems.add(new CartItem(product, Math.min(quantity, product.getQuantity())));
        }
    }
    public List<CartItem > getCartItems(){
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }


    public void updateQuantity(Long productId, int change) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                int newQuantity = item.getQuantity() + change;

                // Ensure the quantity does not exceed the available quantity
                int maxQuantity = item.getProduct().getQuantity();
                if (newQuantity > maxQuantity) {
                    item.setQuantity(maxQuantity);
                } else if (newQuantity < 1) {
                    item.setQuantity(1);
                } else {
                    item.setQuantity(newQuantity);
                }
            }
        }
    }


    public void removeFromCart(Long productId){
        cartItems.removeIf(item->item.getProduct().getId().equals(productId));
    }
    public void clearCart(){
        cartItems.clear();
    }
}