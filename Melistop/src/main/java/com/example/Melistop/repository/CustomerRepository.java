package com.example.Melistop.repository;

import com.example.Melistop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.registrationDate >= :startDate")
    long countNewCustomersSince(@Param("startDate") Date startDate);
}