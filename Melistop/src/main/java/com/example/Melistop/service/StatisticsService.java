//package com.example.Melistop.service;
//
//import com.example.Melistop.repository.CustomerRepository;
//import com.example.Melistop.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Calendar;
//import java.util.Date;
//
//@Service
//public class StatisticsService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public double calculateTotalRevenue() {
//        return orderRepository.calculateTotalRevenue();
//    }
//
//    public long countNewCustomersSince(Date startDate) {
//        return customerRepository.countNewCustomersSince(startDate);
//    }
//
//    public long countNewOrdersSince(Date startDate) {
//        return orderRepository.countNewOrdersSince(startDate);
//    }
//
//    public double calculateProfit() {
//        // Placeholder for actual profit calculation logic
//        // You need to replace this with the actual logic to calculate profit
//        double totalRevenue = calculateTotalRevenue();
//        double expenses = 0; // Replace with actual expense calculation
//        return totalRevenue - expenses;
//    }
//
//    public Date getPreviousPeriodStartDate(Date currentStartDate) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currentStartDate);
//        calendar.add(Calendar.MONTH, -1);
//        return calendar.getTime();
//    }
//
//    public double calculateTotalRevenueSince(Date startDate) {
//        return orderRepository.calculateTotalRevenueSince(startDate);
//    }
//}
