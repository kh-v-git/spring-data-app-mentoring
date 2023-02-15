package com.spring.taskone.demo.service.ticket;

import com.google.common.collect.Lists;
import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.repository.EventRepository;
import com.taskone.demo.repository.TicketRepository;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.service.ticket.TicketServiceImpl;
import com.taskone.demo.utils.TicketCategoryEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
    private static final long EVENT_ID = 10L;
    private static final long USER_ID = 20L;
    private static final long TICKET_ID = 30L;
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 32;
    private static final int PLACE_NUMBER = 128;
    private static final TicketCategoryEnum TICKET_CATEGORY = TicketCategoryEnum.PREMIUM;

    @Mock
    private Event event;

    @Mock
    private Ticket ticket;

    @Mock
    private User user;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketServiceImpl testingInstance;

    @Test
    void shouldBookTicket() {
        when(userRepository.existsById(USER_ID)).thenReturn(Boolean.TRUE);
        when(eventRepository.existsById(EVENT_ID)).thenReturn(Boolean.TRUE);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event));
        when(ticketRepository.save(any())).thenReturn(ticket);
        when(ticket.getSeatNumber()).thenReturn(PLACE_NUMBER);
        when(ticket.getTicketCategory()).thenReturn(TICKET_CATEGORY);

        Ticket result = testingInstance.bookTicket(USER_ID, EVENT_ID, PAGE_NUMBER, TICKET_CATEGORY);

        assertThat(result, is(notNullValue()));
        assertThat(result.getSeatNumber(), is(PLACE_NUMBER));
        assertThat(result.getTicketCategory(), is(TICKET_CATEGORY));
    }

    @Test
    void shouldGetBookedTicketsForUser() {
        List<Ticket> ticketList = Lists.newArrayList(ticket);
        when(ticket.getId()).thenReturn(USER_ID);
        when(ticketRepository.findAllByUser(user)).thenReturn(ticketList);

        List<Ticket> result = testingInstance.getBookedTickets(user, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getId(), is(USER_ID));
    }

    @Test
    void shouldGetBookedTicketsForEvent() {
        List<Ticket> ticketList = Lists.newArrayList(ticket);
        when(ticket.getId()).thenReturn(EVENT_ID);
        when(ticketRepository.findAllByEvent(event)).thenReturn(ticketList);

        List<Ticket> result = testingInstance.getBookedTickets(event, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getId(), is(EVENT_ID));
    }

    @Test
    void shouldCancelTicket() {
        when(ticketRepository.findById(TICKET_ID)).thenReturn(Optional.of(ticket));

        boolean result = testingInstance.cancelTicket(TICKET_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.TRUE));
    }

    @Test
    void shouldNotCancelTicket() {
        when(ticketRepository.findById(TICKET_ID)).thenReturn(Optional.empty());

        boolean result = testingInstance.cancelTicket(TICKET_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.FALSE));
    }
}
