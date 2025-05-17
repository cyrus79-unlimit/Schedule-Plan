package com.jetbrains.cyrus79_unlimit.schedule_plan.entity;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.CreateEventRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String notes;

    private String icon;

    @Enumerated(EnumType.STRING)
    private CreateEventRequest.RecurrenceType recurrence;
}
