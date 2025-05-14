package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.CreateEventRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

    public Event createEvent(CreateEventRequest createEventRequest,String username) {
        User user = userService.findByUsername(username);

        Event newEvent = new Event();
        newEvent.setTitle(createEventRequest.getTitle());
        newEvent.setDescription(createEventRequest.getDescription());
        newEvent.setNotes(createEventRequest.getNote());
        newEvent.setIcon(createEventRequest.getIcon());
        newEvent.setStartTime(createEventRequest.getStartTime());
        newEvent.setEndTime(createEventRequest.getEndTime());
        return eventRepository.save(newEvent);
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findAllByUser_Id(userId);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
