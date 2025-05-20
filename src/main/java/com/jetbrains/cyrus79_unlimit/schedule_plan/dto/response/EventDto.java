package com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private Long eventId;
    private User user;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
    private String icon;
}
