package com.example.employeetaskmanagement.service;

import com.example.employeetaskmanagement.model.Employee;
import com.example.employeetaskmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(@NonNull Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(@NonNull Long id) {
        employeeRepository.deleteById(id);
    }
}