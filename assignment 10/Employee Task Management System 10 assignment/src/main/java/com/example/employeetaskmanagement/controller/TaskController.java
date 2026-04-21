package com.example.employeetaskmanagement.controller;

import com.example.employeetaskmanagement.model.Employee;
import com.example.employeetaskmanagement.model.Task;
import com.example.employeetaskmanagement.service.EmployeeService;
import com.example.employeetaskmanagement.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmployeeService employeeService;

    // ✅ List all tasks
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks/list";   // make sure exists
    }

    // ✅ Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "tasks/add";
    }

    // ✅ Save task
    @PostMapping("/save")
    public String saveTask(@Valid @ModelAttribute("task") Task task,
                           BindingResult result,
                           Model model) {

        // ⚠️ IMPORTANT: reload employees if validation fails
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "tasks/add";
        }

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // ✅ Edit task
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        model.addAttribute("task", task);
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "tasks/add";
    }

    // ✅ Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    // ✅ View tasks for a specific employee
    @GetMapping("/my-tasks/{employeeId}")
    public String listEmployeeTasks(@PathVariable Long employeeId, Model model) {

        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // ⚠️ IMPORTANT: Ensure Employee has getTasks()
        model.addAttribute("tasks", employee.getTasks());
        model.addAttribute("employee", employee);

        return "tasks/my-tasks";   // make sure exists
    }

    // ✅ Update task status
    @PostMapping("/update-status/{id}")
    public String updateTaskStatus(@PathVariable Long id,
                                   @RequestParam String status,
                                   @RequestParam Long employeeId) {

        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setStatus(status);
        taskService.saveTask(task);

        return "redirect:/tasks/my-tasks/" + employeeId;
    }
}