package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateTaskRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {
    TaskDto createTask(String username, CreateTaskRequest request);
    Page<TaskDto> getUserTasks(String username, Boolean completed, Pageable pageable);
}
