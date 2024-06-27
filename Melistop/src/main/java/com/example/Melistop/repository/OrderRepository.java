package com.example.Melistop.repository;

import com.example.Melistop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o")
    double calculateTotalRevenue();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.timeDelivery >= :startDate")
    double calculateTotalRevenueSince(@Param("startDate") Date startDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.timeDelivery >= :startDate")
    long countNewOrdersSince(@Param("startDate") Date startDate);
}
