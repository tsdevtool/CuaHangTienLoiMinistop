//package com.example.Melistop.controllers;
//
//import com.example.Melistop.service.StatisticsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Calendar;
//import java.util.Date;
//
//@Controller
//public class StatisticsController {
//
//    @Autowired
//    private StatisticsService statisticsService;
//
//    @GetMapping("/statistics")
//    public String showStatisticsPage(Model model) {
//        // Define the start date for new customers and orders (e.g., one month ago)
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -1);
//        Date startDate = calendar.getTime();
//
//        // Calculate current statistics
//        double totalRevenue = statisticsService.calculateTotalRevenue();
//        double profit = statisticsService.calculateProfit();
//        long newCustomers = statisticsService.countNewCustomersSince(startDate);
//        long newOrders = statisticsService.countNewOrdersSince(startDate);
//
//        // Calculate previous period start date
//        Date previousStartDate = statisticsService.getPreviousPeriodStartDate(startDate);
//
//        // Calculate previous statistics
//        double previousTotalRevenue = statisticsService.calculateTotalRevenueSince(previousStartDate);
//        long previousNewCustomers = statisticsService.countNewCustomersSince(previousStartDate);
//        long previousNewOrders = statisticsService.countNewOrdersSince(previousStartDate);
//
//        // Calculate changes
//        double revenueChange = calculateChange(totalRevenue, previousTotalRevenue);
//        double customerChange = calculateChange(newCustomers, previousNewCustomers);
//        double orderChange = calculateChange(newOrders, previousNewOrders);
//
//        // Add attributes to model
//        model.addAttribute("totalRevenue", totalRevenue);
//        model.addAttribute("profit", profit);
//        model.addAttribute("newCustomers", newCustomers);
//        model.addAttribute("newOrders", newOrders);
//        model.addAttribute("revenueChange", revenueChange);
//        model.addAttribute("customerChange", customerChange);
//        model.addAttribute("orderChange", orderChange);
//
//        return "admin/statistic/statistic-form";
//    }
//
//    private double calculateChange(double current, double previous) {
//        if (previous == 0) {
//            return current > 0 ? 100 : 0; // 100% increase if there was no previous data and there is current data
//        }
//        return ((current - previous) / previous) * 100;
//    }
//}
