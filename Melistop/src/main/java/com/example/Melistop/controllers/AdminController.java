package com.example.Melistop.controllers;

import com.example.Melistop.models.Category;
import com.example.Melistop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
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
