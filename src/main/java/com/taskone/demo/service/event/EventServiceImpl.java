package com.taskone.demo.service.event;

import com.taskone.demo.domain.Event;
import com.taskone.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event getEventById(final long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException("Event not found with Id = " + eventId));
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return eventRepository.findAllByTitle(title);
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate date, final int pageSize, final int pageNum) {
        return eventRepository.findAllByDate(date);
    }

    @Override
    public Event createEvent(final Event event) {
        Event savedEvent = eventRepository.save(event);

        log.debug("New Event with ID {} was created.", savedEvent.getId());

        return savedEvent;
    }

    @Override
    public Event updateEvent(final Event event) {
        Event savedEvent = eventRepository.save(event);

        log.debug("Event with ID {} was updated.", savedEvent.getId());

        return savedEvent;
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        Optional<Event> maybeEvent = eventRepository.findById(eventId);
        if (maybeEvent.isEmpty()) {
            log.debug("Event with ID {} was NOT deleted cause NOT found.", eventId);

            return false;
        }
        eventRepository.delete(maybeEvent.get());

        log.debug("Event with ID {} was deleted.", eventId);

        return true;
    }
}



