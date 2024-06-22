package com.example.Melistop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminController {

    @GetMapping("/mainAdmin")
    public String admin() {
        return "admin/main";
    }

    @GetMapping("/indexAdmin")
    public String indexAdmin() {
        return "admin/index";
    }

    @GetMapping("/add")
    public String hien() {
        return "employee/employee-add";
    }



}
