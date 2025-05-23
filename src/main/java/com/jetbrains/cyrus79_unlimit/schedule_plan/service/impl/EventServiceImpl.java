package com.jetbrains.cyrus79_unlimit.schedule_plan.service.impl;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.CreateEventRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.EventRepository;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.EventService;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
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
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final UserService userService;

    @Override
    public List<Event> createEvent(String email, CreateEventRequest createEventRequest) {
        User user = userService.findByEmail(email);
        List<Event> events = new ArrayList<>();

        LocalDateTime start = createEventRequest.getStartTime();
        LocalDateTime end = createEventRequest.getEndTime();
        CreateEventRequest.RecurrenceType recurrence = createEventRequest.getRecurrence();

        for (int i = 0; i < 10; i++) {
            Event event = new Event();
            event.setTitle(createEventRequest.getTitle());
            event.setDescription(createEventRequest.getDescription());
            event.setNotes(createEventRequest.getNote());
            event.setIcon(createEventRequest.getIcon());
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

    @Override
    public Page<Event> getUserEvents(String email, Pageable pageable) {
        User user = userService.findByEmail(email);
        return eventRepository.findAllByUser_Id(user.getId(),pageable);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
