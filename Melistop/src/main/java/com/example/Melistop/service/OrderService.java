package com.example.Melistop.service;

import com.example.Melistop.models.CartItem;
import com.example.Melistop.models.Order;
import com.example.Melistop.models.OrderDetail;
import com.example.Melistop.models.User;
import com.example.Melistop.repository.OrderDetailRepository;
import com.example.Melistop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService;

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public Order createOrder(User user, List<CartItem> cartItems){
        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);

        for(CartItem item: cartItems){
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getQuantity()*item.getProduct().getPrice());
            orderDetailRepository.save(detail);
        }

        //Xoa san pham trong gio hang
        cartService.clearCart();
        return order;
    }
}
