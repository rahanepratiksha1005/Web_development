package com.example.employeetaskmanagement.service;

import com.example.employeetaskmanagement.model.Task;
import com.example.employeetaskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(@NonNull Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(@NonNull Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(@NonNull Long id) {
        taskRepository.deleteById(id);
    }
}