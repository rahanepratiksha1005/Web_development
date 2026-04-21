package com.college.etms.controller;

import com.college.etms.dto.common.ApiResponse;
import com.college.etms.dto.common.PageResponse;
import com.college.etms.dto.task.TaskRequestDto;
import com.college.etms.dto.task.TaskResponseDto;
import com.college.etms.dto.task.TaskStatusUpdateDto;
import com.college.etms.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(requestDto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TaskResponseDto>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deadline") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(page, size, sortBy, sortDir));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TaskResponseDto>> getTaskList() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<PageResponse<TaskResponseDto>> getTasksByEmployee(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "deadline") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(employeeId, page, size, sortBy, sortDir));
    }

    @GetMapping("/employee/{employeeId}/list")
    public ResponseEntity<List<TaskResponseDto>> getTaskListByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(employeeId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDto requestDto
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, requestDto));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskStatusUpdateDto requestDto
    ) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, requestDto.getStatus()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(ApiResponse.builder().message("Task deleted successfully").build());
    }
}
