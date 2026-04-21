package com.college.etms.service.impl;

import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.employee.EmployeeRequestDto;
import com.college.etms.dto.employee.EmployeeResponseDto;
import com.college.etms.entity.Employee;
import com.college.etms.exception.BadRequestException;
import com.college.etms.exception.DuplicateResourceException;
import com.college.etms.exception.ResourceNotFoundException;
import com.college.etms.repository.EmployeeRepository;
import com.college.etms.service.EmployeeService;
import com.college.etms.util.PageResponseBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        validateEmailUniqueness(requestDto.getEmail(), null);

        Employee employee = Employee.builder()
                .name(requestDto.getName().trim())
                .email(requestDto.getEmail().trim().toLowerCase())
                .department(requestDto.getDepartment().trim())
                .designation(requestDto.getDesignation().trim())
                .build();

        return mapToResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String sortDir) {
        Page<Employee> employeePage = employeeRepository.findAll(
                PageRequest.of(page, size, buildSort(sortBy, sortDir))
        );

        return PageResponseBuilder.build(
                employeePage.map(this::mapToResponse),
                sortBy,
                sortDir
        );
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long employeeId) {
        return mapToResponse(fetchEmployee(employeeId));
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long employeeId, EmployeeRequestDto requestDto) {
        Employee employee = fetchEmployee(employeeId);
        validateEmailUniqueness(requestDto.getEmail(), employeeId);

        employee.setName(requestDto.getName().trim());
        employee.setEmail(requestDto.getEmail().trim().toLowerCase());
        employee.setDepartment(requestDto.getDepartment().trim());
        employee.setDesignation(requestDto.getDesignation().trim());

        return mapToResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = fetchEmployee(employeeId);
        if (!employee.getTasks().isEmpty()) {
            throw new BadRequestException("Employee cannot be deleted because tasks are still assigned");
        }
        employeeRepository.delete(employee);
    }

    private Employee fetchEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
    }

    private void validateEmailUniqueness(String email, Long employeeId) {
        String normalizedEmail = email.trim().toLowerCase();

        boolean exists = employeeId == null
                ? employeeRepository.existsByEmailIgnoreCase(normalizedEmail)
                : employeeRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, employeeId);

        if (exists) {
            throw new DuplicateResourceException("Employee email already exists: " + normalizedEmail);
        }
    }

    private Sort buildSort(String sortBy, String sortDir) {
        List<String> allowedFields = List.of("id", "name", "email", "department", "designation");
        if (!allowedFields.contains(sortBy)) {
            throw new BadRequestException("Invalid employee sort field: " + sortBy);
        }
        return "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private EmployeeResponseDto mapToResponse(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .build();
    }
}
