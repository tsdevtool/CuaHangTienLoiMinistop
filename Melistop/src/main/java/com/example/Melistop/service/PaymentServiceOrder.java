package com.example.Melistop.service;

import com.example.Melistop.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentServiceOrder {
    List<Payment> findAllPayments();
    Optional<Payment> findPaymentById(Long id);
    Payment findByName(String name);
    Payment savePayment(Payment payment);
    void deletePaymentById(Long id);
}
