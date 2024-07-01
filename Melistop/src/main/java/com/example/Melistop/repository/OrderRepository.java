package com.example.Melistop.repository;


import com.example.Melistop.models.Order;
import com.example.Melistop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByStatusTrue(); // Tìm các đơn hàng đã giao

    List<Order> findByStatusFalseOrStatusIsNull(); // Tìm các đơn hàng chưa giao
}
