package com.example.Melistop.controllers;

import com.example.Melistop.models.Customer;
import com.example.Melistop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin/customer/customer-list";
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customer/customer-add";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/admin/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return "redirect:/admin/customers";
        }
        model.addAttribute("customer", customer);
        return "admin/customer/customer-edit";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer != null) {
            if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
                customer.setPassword(existingCustomer.getPassword());
            }
            customer.setId(id);
            customerService.saveCustomer(customer);
        }
        return "redirect:/admin/customers";
    }

    @GetMapping("/details/{id}")
    public String showCustomerDetails(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return "redirect:/admin/customers";
        }
        model.addAttribute("customer", customer);
        return "admin/customer/customer-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers";
    }
}
