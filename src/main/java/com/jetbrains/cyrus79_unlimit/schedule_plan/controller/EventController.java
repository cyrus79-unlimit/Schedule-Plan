package com.jetbrains.cyrus79_unlimit.schedule_plan.controller;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.CreateEventRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@SecurityRequirement(name = "bearerAuth")
public class EventController {
    @Autowired
    private EventService eventService;

    // Create Event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest createEventRequest) {
        // Set the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Event createdEven = eventService.createEvent(createEventRequest,username);
        return ResponseEntity.ok(createdEven);
    }

    // Get Events by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getEventsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventsByUserId(userId));
    }

    // Get Event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
