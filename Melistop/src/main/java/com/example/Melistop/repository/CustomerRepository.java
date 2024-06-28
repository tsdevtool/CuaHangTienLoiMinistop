package com.example.Melistop.repository;

import com.example.Melistop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.registrationDate >= :startDate")
    long countNewCustomersSince(@Param("startDate") Date startDate);

    boolean existsByNumberPhone(String numberPhone);
    boolean existsByEmail(String email);
    Optional<Customer> findByNumberPhone(String numberPhone);
}
