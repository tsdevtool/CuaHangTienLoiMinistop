package com.example.Melistop.service;

import com.example.Melistop.models.Payment;
import com.example.Melistop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }
}
