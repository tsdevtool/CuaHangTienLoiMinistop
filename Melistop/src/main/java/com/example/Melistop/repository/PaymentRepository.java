package com.example.Melistop.repository;

import com.example.Melistop.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    Payment findById(Long id);
}
