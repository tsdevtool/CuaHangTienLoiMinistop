package com.example.Melistop.repository;


import com.example.Melistop.DTO.DailyRevenue;
import com.example.Melistop.DTO.MonthlyRevenue;
import com.example.Melistop.models.Order;
import com.example.Melistop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByStatusTrue(); // Tìm các đơn hàng đã giao

    List<Order> findByStatusFalseOrStatusIsNull(); // Tìm các đơn hàng chưa giao

//    @Query("SELECT new com.example.Melistop.models.DailyRevenue(o.orderDate, SUM(o.totalPrice)) " +
//            "FROM Order o " +
//            "WHERE o.status = true " +
//            "AND FUNCTION('MONTH', o.orderDate) = :month " +
//            "AND FUNCTION('YEAR', o.orderDate) = :year " +
//            "GROUP BY o.orderDate")
//    List<DailyRevenue> findDailyRevenue(int month, int year);
//
//    @Query("SELECT new com.example.Melistop.models.MonthlyRevenue(FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate), SUM(o.totalPrice)) " +
//            "FROM Order o " +
//            "WHERE o.status = true AND FUNCTION('YEAR', o.orderDate) = :year " +
//            "GROUP BY FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate)")
//    List<MonthlyRevenue> findMonthlyRevenue(int year);
}
