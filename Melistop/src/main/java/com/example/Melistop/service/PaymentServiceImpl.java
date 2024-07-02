package com.example.Melistop.service;

import com.example.Melistop.models.Payment;
import com.example.Melistop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentServiceOrder {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment findByName(String name) {
        return paymentRepository.findByName(name);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePaymentById(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalStateException("Payment with ID " + id + " does not exist.");
        }
        paymentRepository.deleteById(id);
    }
}
