package com.example.employeetaskmanagement.controller;

import com.example.employeetaskmanagement.model.Employee;
import com.example.employeetaskmanagement.service.EmployeeService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ List all employees
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";   // make sure this HTML exists
    }

    // ✅ Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/add";   // make sure this HTML exists
    }

    // ✅ Save employee
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result) {

        // ⚠️ IMPORTANT: BindingResult must come right after @Valid
        if (result.hasErrors()) {
            return "employees/add";
        }

        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // ✅ Edit employee
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        model.addAttribute("employee", employee);
        return "employees/add";   // reuse same form
    }

    // ✅ Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}