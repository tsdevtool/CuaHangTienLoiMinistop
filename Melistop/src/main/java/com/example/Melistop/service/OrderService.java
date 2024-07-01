package com.example.Melistop.service;

import com.example.Melistop.models.*;
import com.example.Melistop.repository.OrderDetailRepository;
import com.example.Melistop.repository.OrderRepository;
import com.example.Melistop.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private PaymentRepository paymentRepository;
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    private double totalPrice(List<CartItem> cartItems){
        double sum = 0;
        for(CartItem cartItem: cartItems){
            sum+=cartItem.getQuantity()*cartItem.getProduct().getPrice();
        }
        return sum;
    }
    @Transactional
    public Order createOrder(User user, String addAddress, String receiveTime, String description, String delivery, Long paymentId, List<CartItem> cartItems){

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setReceiveTime(receiveTime);
        order.setAddressShip(addAddress);
        order.setDescription(description);
        order.setDelivery(delivery);
        order.setStatus(false);
        order.setTotalPrice(totalPrice(cartItems));
        order.setPayment(paymentRepository.findById(paymentId).get());
        order = orderRepository.save(order);

        for(CartItem item: cartItems){
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getQuantity()*item.getProduct().getPrice());
            orderDetailRepository.save(detail);
            cartService.removeProductInCartOfUser2(item.getId(), user);
        }

        //Xoa san pham trong gio hang
//        cartService.clearCart();
        return order;
    }
}
