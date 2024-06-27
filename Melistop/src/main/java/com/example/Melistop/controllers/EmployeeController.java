package com.example.Melistop.controllers;

import com.example.Melistop.models.Employee;
import com.example.Melistop.service.EmployeeService;
import com.example.Melistop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/employee/employee-list";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/employee/employee-add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/admin/employees";
        }
        model.addAttribute("employee", employee);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/employee/employee-edit";
    }

    @PostMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        Employee existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee != null) {
            if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
                employee.setPassword(existingEmployee.getPassword());
            }
            employee.setId(id);
            employeeService.saveEmployee(employee);
        }
        return "redirect:/admin/employees";
    }

    @GetMapping("/details/{id}")
    public String showEmployeeDetails(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/admin/employees";
        }
        model.addAttribute("employee", employee);
        return "admin/employee/employee-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }
}
