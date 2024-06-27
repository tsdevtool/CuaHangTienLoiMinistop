package com.example.Melistop.service;
import com.example.Melistop.models.Order;
import com.example.Melistop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByDate(Date date) {
        return orderRepository.findByDate(date);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void updateOrder(Long id, Order orderDetails) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            // Update fields
            order.setAddressDelivery(orderDetails.getAddressDelivery());
            order.setNote(orderDetails.getNote());
            order.setPayment(orderDetails.getPayment());
            order.setTimeDelivery(orderDetails.getTimeDelivery());
            order.setEmployee(orderDetails.getEmployee());
            order.setTotalPrice(orderDetails.getTotalPrice());
            // Save updated order
            orderRepository.save(order);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
