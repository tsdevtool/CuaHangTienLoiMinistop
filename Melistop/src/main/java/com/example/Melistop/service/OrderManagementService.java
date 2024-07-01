package com.example.Melistop.service;

import com.example.Melistop.models.Order;
import com.example.Melistop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderManagementService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findDeliveredOrders() {
        return orderRepository.findByStatusTrue();
    }

    public List<Order> findPendingOrders() {
        return orderRepository.findByStatusFalseOrStatusIsNull();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setId(id); // Ensure the ID is set to update the existing order
            orderRepository.save(updatedOrder);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
