package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateEventRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> createEvent(String username,CreateEventRequest createEventRequest);
    Page<Event> getUserEvents(String username, Pageable pageable);
    Optional<Event> getEventById(Long id);
    void deleteEvent(Long id);
}
