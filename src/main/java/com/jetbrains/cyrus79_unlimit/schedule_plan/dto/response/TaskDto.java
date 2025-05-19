package com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long taskId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private boolean completed;
}
