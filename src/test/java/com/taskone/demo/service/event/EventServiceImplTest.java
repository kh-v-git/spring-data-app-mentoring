package com.taskone.demo.service.event;

import com.google.common.collect.Lists;
import com.taskone.demo.domain.Event;
import com.taskone.demo.repository.EventRepository;
import com.taskone.demo.service.event.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    private static final long EVENT_ID = 10L;
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 32;
    private static final String EVENT_TITLE = "Test Title";
    private static final LocalDate EVENT_DATE = LocalDate.of(2023, 10, 12);

    @Mock
    private Event event;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl testingInstance;

    @Test
    void shouldGetEventById() {
        when(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        when(event.getId()).thenReturn(EVENT_ID);

        Event result = testingInstance.getEventById(EVENT_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(EVENT_ID));
    }

    @Test
    void shouldGetEventsByTitle() {
        List<Event> eventList = Lists.newArrayList(event);
        when(event.getTitle()).thenReturn(EVENT_TITLE);
        when(eventRepository.findAllByTitle(EVENT_TITLE)).thenReturn(eventList);

        List<Event> result = testingInstance.getEventsByTitle(EVENT_TITLE, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getTitle(), is(EVENT_TITLE));
    }

    @Test
    void shouldGetEventsForDay() {
        List<Event> eventList = Lists.newArrayList(event);
        when(event.getDate()).thenReturn(EVENT_DATE);
        when(eventRepository.findAllByDate(EVENT_DATE)).thenReturn(eventList);

        List<Event> result = testingInstance.getEventsForDay(EVENT_DATE, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getDate(), is(EVENT_DATE));
    }

    @Test
    void shouldCreateEvent() {
        when(eventRepository.save(event)).thenReturn(event);
        when(event.getId()).thenReturn(EVENT_ID);
        when(event.getTitle()).thenReturn(EVENT_TITLE);
        when(event.getDate()).thenReturn(EVENT_DATE);

        Event result = testingInstance.createEvent(event);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(EVENT_ID));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        assertThat(result.getDate(), is(EVENT_DATE));
    }

    @Test
    void shouldUpdateEvent() {
        when(eventRepository.save(event)).thenReturn(event);
        when(event.getId()).thenReturn(EVENT_ID);
        when(event.getDate()).thenReturn(EVENT_DATE);
        when(event.getTitle()).thenReturn(EVENT_TITLE);

        Event result = testingInstance.updateEvent(event);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(EVENT_ID));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        assertThat(result.getDate(), is(EVENT_DATE));
    }

    @Test
    void shouldDeleteEvent() {
        when(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));

        boolean result = testingInstance.deleteEvent(EVENT_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.TRUE));
    }

    @Test
    void shouldNotDeleteEvent() {
        when(eventRepository.findById(EVENT_ID)).thenReturn(Optional.empty());

        boolean result = testingInstance.deleteEvent(EVENT_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.FALSE));
    }
}


