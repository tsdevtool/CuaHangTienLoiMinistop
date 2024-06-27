package com.example.Melistop.service;

import com.example.Melistop.models.Employee;
import com.example.Melistop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employees", e);
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            return employee.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employee by id", e);
        }
    }

    public void saveEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error saving employee", e);
        }
    }

    public void deleteEmployee(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee", e);
        }
    }
}
