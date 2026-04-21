package com.college.etms.service;

import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.employee.EmployeeRequestDto;
import com.college.etms.dto.employee.EmployeeResponseDto;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

    List<EmployeeResponseDto> getAllEmployees();

    PageResponse<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String sortDir);

    EmployeeResponseDto getEmployeeById(Long employeeId);

    EmployeeResponseDto updateEmployee(Long employeeId, EmployeeRequestDto requestDto);

    void deleteEmployee(Long employeeId);
}
