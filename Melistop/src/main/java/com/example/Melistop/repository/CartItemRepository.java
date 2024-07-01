package com.example.Melistop.repository;

import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.Product;
import com.example.Melistop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
    List<CartItem> findByUser(User user);
}
