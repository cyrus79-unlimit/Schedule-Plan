package com.jetbrains.cyrus79_unlimit.schedule_plan.repository;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByUser_Id(Long userId, Pageable pageable);
    Page<Task> findTaskByUserIdAndCompleted(Long userId, boolean completed, Pageable pageable);
}
