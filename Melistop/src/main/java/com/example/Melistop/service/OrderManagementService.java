package com.example.Melistop.service;

import com.example.Melistop.DTO.DailyRevenue;
import com.example.Melistop.DTO.MonthlyRevenue;
import com.example.Melistop.models.Order;
import com.example.Melistop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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

    //Tinh tong doanh thu tung ngay theo thang
    public List<DailyRevenue> getDailyRevenue() {
        List<DailyRevenue> dailyRevenues = new ArrayList<>();
        List<Order> orders = orderRepository.findByStatusTrue();
        for(Order order:orders){
            if(dailyRevenues.isEmpty()){
                DailyRevenue dailyRevenue = new DailyRevenue(order.getOrderDate(), order.getTotalPrice());
                dailyRevenues.add(dailyRevenue);
            }else{
                for(DailyRevenue dailyRevenue: dailyRevenues){
                    if(dailyRevenue.getDate() == order.getOrderDate()){
                        dailyRevenue.setTotal(dailyRevenue.getTotal() + order.getTotalPrice());
                    }else{
                        DailyRevenue dailyRevenue2 = new DailyRevenue(order.getOrderDate(), order.getTotalPrice());
                        dailyRevenues.add(dailyRevenue2);
                    }
                }
            }
        }
        return dailyRevenues;
    }

    //Tinh tong doanh thu tung thang trong nam,
    public List<MonthlyRevenue> getMonthlyRevenue() {
        List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();
        List<Order> orders = orderRepository.findByStatusTrue();
        for(Order order:orders){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            if(monthlyRevenues.isEmpty()){
                MonthlyRevenue monthlyRevenue = new MonthlyRevenue(year, month, order.getTotalPrice());
                monthlyRevenues.add(monthlyRevenue);
            }else{
                for(MonthlyRevenue monthlyRevenue:monthlyRevenues){
                    if(monthlyRevenue.getMonth() == month && monthlyRevenue.getYear() == year){
                        monthlyRevenue.setRevenue(monthlyRevenue.getRevenue() + order.getTotalPrice());
                    }else{
                        MonthlyRevenue monthlyRevenue2 = new MonthlyRevenue(year, month, order.getTotalPrice());
                        monthlyRevenues.add(monthlyRevenue2);
                    }
                }
            }
        }
        return monthlyRevenues;
    }
}
