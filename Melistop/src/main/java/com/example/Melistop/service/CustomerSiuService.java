package com.example.Melistop.service;

import com.example.Melistop.DTO.CustomerLoginDTO;
import com.example.Melistop.DTO.CustomerRegistrationDTO;
import com.example.Melistop.models.Customer;
import com.example.Melistop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerSiuService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerRegistrationDTO registrationDTO){
        //Kiem tra xem tai khoan da co trong data chua
        //Neu co thong bao tai khoan da dang ki
        if(customerRepository.existsByNumberPhone(registrationDTO.getNumberPhone())){
            throw new RuntimeException("Số điện thoại đã được đăng kí");
        }
        if(customerRepository.existsByEmail(registrationDTO.getEmail())){
            throw new RuntimeException("Email đã được đăng ký");
        }

        Customer customer = new Customer();
        customer.setNumberPhone(registrationDTO.getNumberPhone());
        customer.setPassword(new BCryptPasswordEncoder(registrationDTO.getPassword()));
        customer.setLastName(registrationDTO.getLastName());
        customer.setFirstName(registrationDTO.getFirstName());
        customer.setEmail(registrationDTO.getEmail());
        customer.setRegistrationDate(new Date());
        customer.setIsLocked(false);

        return customerRepository.save(customer);
    }

    public Customer loginCustomer(CustomerLoginDTO loginDTO) {
        Customer customer = customerRepository.findByNumberPhone(loginDTO.getNumberPhone())
                .orElseThrow(() -> new RuntimeException("Số điện thoại không tồn tại"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        return customer;
    }

}
