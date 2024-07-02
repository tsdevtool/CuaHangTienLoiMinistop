package com.example.Melistop.repository;

import com.example.Melistop.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByName(String name);
}
