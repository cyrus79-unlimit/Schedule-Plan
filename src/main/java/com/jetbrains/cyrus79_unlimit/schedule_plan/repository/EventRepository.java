package com.jetbrains.cyrus79_unlimit.schedule_plan.repository;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findAllByUser_Id(Long userId);
}
