package com.jetbrains.cyrus79_unlimit.schedule_plan.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    private String title;
    private String description;
    private String note;
    private String icon;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
