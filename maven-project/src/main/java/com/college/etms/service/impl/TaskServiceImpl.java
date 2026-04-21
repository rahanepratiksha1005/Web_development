package com.college.etms.service.impl;

import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.task.TaskRequestDto;
import com.college.etms.dto.task.TaskResponseDto;
import com.college.etms.entity.Employee;
import com.college.etms.entity.Task;
import com.college.etms.enums.TaskStatus;
import com.college.etms.exception.BadRequestException;
import com.college.etms.exception.ResourceNotFoundException;
import com.college.etms.repository.EmployeeRepository;
import com.college.etms.repository.TaskRepository;
import com.college.etms.service.TaskService;
import com.college.etms.util.PageResponseBuilder;
import java.time.LocalDate;
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
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Employee employee = fetchEmployee(requestDto.getAssignedEmployeeId());
        Task task = buildTaskFromRequest(new Task(), requestDto, employee);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "deadline"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponseDto> getAllTasks(int page, int size, String sortBy, String sortDir) {
        Page<Task> taskPage = taskRepository.findAll(PageRequest.of(page, size, buildSort(sortBy, sortDir)));
        return PageResponseBuilder.build(taskPage.map(this::mapToResponse), sortBy, sortDir);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long taskId) {
        return mapToResponse(fetchTask(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getTasksByEmployee(Long employeeId) {
        ensureEmployeeExists(employeeId);
        return taskRepository.findByAssignedEmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponseDto> getTasksByEmployee(Long employeeId, int page, int size, String sortBy, String sortDir) {
        ensureEmployeeExists(employeeId);
        Page<Task> taskPage = taskRepository.findByAssignedEmployeeId(
                employeeId,
                PageRequest.of(page, size, buildSort(sortBy, sortDir))
        );
        return PageResponseBuilder.build(taskPage.map(this::mapToResponse), sortBy, sortDir);
    }

    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto requestDto) {
        Task task = fetchTask(taskId);
        Employee employee = fetchEmployee(requestDto.getAssignedEmployeeId());
        buildTaskFromRequest(task, requestDto, employee);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponseDto updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = fetchTask(taskId);
        task.setStatus(status);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = fetchTask(taskId);
        taskRepository.delete(task);
    }

    private Task buildTaskFromRequest(Task task, TaskRequestDto requestDto, Employee employee) {
        if (requestDto.getDeadline().isBefore(LocalDate.now()) && requestDto.getStatus() != TaskStatus.COMPLETED) {
            throw new BadRequestException("Deadline cannot be in the past for a non-completed task");
        }

        task.setTitle(requestDto.getTitle().trim());
        task.setDescription(requestDto.getDescription().trim());
        task.setAssignedEmployee(employee);
        task.setDeadline(requestDto.getDeadline());
        task.setStatus(requestDto.getStatus());
        task.setPriority(requestDto.getPriority());
        return task;
    }

    private Employee fetchEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
    }

    private void ensureEmployeeExists(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
    }

    private Task fetchTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
    }

    private Sort buildSort(String sortBy, String sortDir) {
        List<String> allowedFields = List.of("id", "title", "deadline", "status", "priority");
        if (!allowedFields.contains(sortBy)) {
            throw new BadRequestException("Invalid task sort field: " + sortBy);
        }
        return "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
    }

    private TaskResponseDto mapToResponse(Task task) {
        Employee employee = task.getAssignedEmployee();
        boolean overdue = task.getDeadline().isBefore(LocalDate.now()) && task.getStatus() != TaskStatus.COMPLETED;

        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .priority(task.getPriority())
                .overdue(overdue)
                .assignedEmployee(TaskResponseDto.EmployeeSummaryDto.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .email(employee.getEmail())
                        .department(employee.getDepartment())
                        .designation(employee.getDesignation())
                        .build())
                .build();
    }
}
