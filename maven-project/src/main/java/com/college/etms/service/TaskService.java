package com.college.etms.service;

import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.task.TaskRequestDto;
import com.college.etms.dto.task.TaskResponseDto;
import com.college.etms.enums.TaskStatus;
import java.util.List;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto requestDto);

    List<TaskResponseDto> getAllTasks();

    PageResponse<TaskResponseDto> getAllTasks(int page, int size, String sortBy, String sortDir);

    TaskResponseDto getTaskById(Long taskId);

    List<TaskResponseDto> getTasksByEmployee(Long employeeId);

    PageResponse<TaskResponseDto> getTasksByEmployee(Long employeeId, int page, int size, String sortBy, String sortDir);

    TaskResponseDto updateTask(Long taskId, TaskRequestDto requestDto);

    TaskResponseDto updateTaskStatus(Long taskId, TaskStatus status);

    void deleteTask(Long taskId);
}
