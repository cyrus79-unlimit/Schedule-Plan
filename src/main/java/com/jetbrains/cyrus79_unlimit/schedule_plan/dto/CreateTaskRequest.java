package com.jetbrains.cyrus79_unlimit.schedule_plan.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;

}
