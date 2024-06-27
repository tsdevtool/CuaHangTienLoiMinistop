package com.example.Melistop.service;

import com.example.Melistop.models.Customer;
import com.example.Melistop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customers", e);
        }
    }

    public Customer getCustomerById(Long id) {
        try {
            Optional<Customer> customer = customerRepository.findById(id);
            return customer.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customer by id", e);
        }
    }

    public void saveCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException("Error saving customer", e);
        }
    }

    public void deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }
}
