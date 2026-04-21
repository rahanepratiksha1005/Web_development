package com.college.etms.dto.task;

import com.college.etms.enums.Priority;
import com.college.etms.enums.TaskStatus;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TaskResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDate deadline;
    private final TaskStatus status;
    private final Priority priority;
    private final boolean overdue;
    private final EmployeeSummaryDto assignedEmployee;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class EmployeeSummaryDto {
        private final Long id;
        private final String name;
        private final String email;
        private final String department;
        private final String designation;
    }
}
