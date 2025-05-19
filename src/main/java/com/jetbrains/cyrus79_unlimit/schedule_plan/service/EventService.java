package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateEventRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final UserService userService;

    public List<Event> createEvent(CreateEventRequest request, String username) {
        User user = userService.findByUsername(username);
        List<Event> events = new ArrayList<>();

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime();
        CreateEventRequest.RecurrenceType recurrence = request.getRecurrence();

        for (int i = 0; i < 10; i++) {
            Event event = new Event();
            event.setTitle(request.getTitle());
            event.setDescription(request.getDescription());
            event.setNotes(request.getNote());
            event.setIcon(request.getIcon());
            event.setStartTime(start);
            event.setEndTime(end);
            event.setUser(user);
            event.setRecurrence(recurrence);
            events.add(eventRepository.save(event));

            // Prepare next recurrence
            switch (recurrence) {
                case DAILY -> {
                    start = start.plusDays(1);
                    end = end.plusDays(1);
                }
                case WEEKLY -> {
                    start = start.plusWeeks(1);
                    end = end.plusWeeks(1);
                }
                case MONTHLY -> {
                    start = start.plusMonths(1);
                    end = end.plusMonths(1);
                }
                case NONE -> i = 10; // break the loop
            }
        }
        return events;
    }

    public Page<Event> getUserEvents(String username, Pageable pageable) {
        User user = userService.findByUsername(username);
        return eventRepository.findAllByUser_Id(user.getId(),pageable);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
