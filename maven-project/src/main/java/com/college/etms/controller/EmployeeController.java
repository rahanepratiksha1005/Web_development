package com.college.etms.controller;

import com.college.etms.dto.common.ApiResponse;
import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.employee.EmployeeRequestDto;
import com.college.etms.dto.employee.EmployeeResponseDto;
import com.college.etms.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(requestDto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<EmployeeResponseDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(employeeService.getAllEmployees(page, size, sortBy, sortDir));
    }

    @GetMapping("/list")
    public ResponseEntity<java.util.List<EmployeeResponseDto>> getEmployeeList() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeRequestDto requestDto
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, requestDto));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.builder().message("Employee deleted successfully").build());
    }
}
