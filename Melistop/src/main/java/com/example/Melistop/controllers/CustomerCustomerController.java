package com.example.Melistop.controllers;

import com.example.Melistop.models.Customer;
import com.example.Melistop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerCustomerController {

    @Autowired
    private CustomerService customerService;

    //Hien thi form dang ky
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    //Xu ly them khach hang moi
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {
        try {
            customerService.register(customer);
            model.addAttribute("message", "Đăng kí tài khoản thành công");
            return "/item/ProductList";
        } catch (RuntimeException ex) {
            model.addAttribute("message", ex.getMessage());
            return "/login";
        }
    }

    //Hien thi form dang nhap
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }

    //Xu ly su kien dang nhap
    public String loginCustomer(@RequestParam String phoneNumber, @RequestParam String password, Model model) {
        try {
            Customer customer = customerService.login(phoneNumber, password);
            model.addAttribute("message", "Đăng nhập thành công");
            return "item/ProductList";
        } catch (RuntimeException ex) {
            model.addAttribute("message", ex.getMessage());
            return "/login";
        }
    }
}