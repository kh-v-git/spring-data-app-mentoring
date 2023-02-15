package com.taskone.demo.service.event;

import com.taskone.demo.domain.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {
    Event getEventById(long eventId);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);
}
