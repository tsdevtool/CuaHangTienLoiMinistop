package com.example.Melistop.service;

import com.example.Melistop.models.Customer;
import com.example.Melistop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

//    @Autowired
//    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer register(Customer customer){
        //Kiem tra xem da co tai khoan dung so dien thoai nay chua
        //Neu co roi thi thong bao da co tai khoan roi
        if(customerRepository.findByPhoneNumber(customer.getNumberPhone()).isPresent()){
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer login(String phoneNumber, String password){
        //Lay thong tin nguoi dung voi so dien thoai da luu
        //Neu khong co thi bao khong co tai khoan nay
        //Neu co thi kiem tra password
        Optional<Customer> customerOptional = customerRepository.findByPhoneNumber(phoneNumber);
        if(customerOptional.isPresent()){
            //Kiem tra mat khau neu dung mat khau thi tra ve thong tin customer;
            //Neu khong thi thong bao mat khau sai
            Customer customer = customerOptional.get();
            if(passwordEncoder.matches(password, customer.getPassword())){
                return customer;
            }
            throw new RuntimeException("Mật khẩu không đúng");
        }else{
            throw new RuntimeException("Không tìm thấy thông tin cho số điện thoại này");
        }
    }
}
