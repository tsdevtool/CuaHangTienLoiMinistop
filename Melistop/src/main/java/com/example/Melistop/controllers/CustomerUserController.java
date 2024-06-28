package com.example.Melistop.controllers;

import com.example.Melistop.DTO.CustomerLoginDTO;
import com.example.Melistop.DTO.CustomerRegistrationDTO;
import com.example.Melistop.models.Customer;
import com.example.Melistop.service.CustomerService;
import com.example.Melistop.service.CustomerSiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerUserController {

    @Autowired
    private CustomerSiuService customerService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customerRegistrationDTO", new CustomerRegistrationDTO());
        return "/user/register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Validated @ModelAttribute CustomerRegistrationDTO registrationDTO, Model model) {
        try {
            Customer registeredCustomer = customerService.registerCustomer(registrationDTO);
            model.addAttribute("message", "Đăng ký thành công");
            return "login";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customerLoginDTO", new CustomerLoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginCustomer(@Validated @ModelAttribute CustomerLoginDTO loginDTO, Model model) {
        try {
            Customer loggedInCustomer = customerService.loginCustomer(loginDTO);
            model.addAttribute("message", "Đăng nhập thành công");
            model.addAttribute("customer", loggedInCustomer);
            return "/item/ProductList"; // Trang chủ sau khi đăng nhập thành công
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }
}

