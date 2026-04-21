package com.college.etms.repository;

import com.college.etms.entity.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Override
    @EntityGraph(attributePaths = "assignedEmployee")
    Page<Task> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "assignedEmployee")
    List<Task> findAll();

    @EntityGraph(attributePaths = "assignedEmployee")
    Page<Task> findByAssignedEmployeeId(Long employeeId, Pageable pageable);

    @EntityGraph(attributePaths = "assignedEmployee")
    List<Task> findByAssignedEmployeeId(Long employeeId);
}
