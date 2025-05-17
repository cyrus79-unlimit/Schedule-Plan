package com.jetbrains.cyrus79_unlimit.schedule_plan.repository;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByUser_Id(Long userId, Pageable pageable);

}
