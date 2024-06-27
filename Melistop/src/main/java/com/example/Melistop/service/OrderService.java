package com.example.Melistop.service;

import com.example.Melistop.models.Order;
import com.example.Melistop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public double calculateTotalRevenue() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().mapToDouble(Order::getTotalPrice).sum();
    }
}
