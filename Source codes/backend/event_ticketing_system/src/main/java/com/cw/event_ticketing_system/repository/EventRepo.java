package com.cw.event_ticketing_system.repository;

import com.cw.event_ticketing_system.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Long> {
    //Event findEventByEventId(Long id);
}
