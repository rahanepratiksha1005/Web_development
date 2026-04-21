package com.college.etms.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String department;
    private final String designation;
}
