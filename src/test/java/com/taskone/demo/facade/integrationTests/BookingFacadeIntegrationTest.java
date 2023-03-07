package com.taskone.demo.facade.integrationTests;

import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.domain.UserAccount;
import com.taskone.demo.facade.BookingFacade;
import com.taskone.demo.repository.EventRepository;
import com.taskone.demo.repository.TicketRepository;
import com.taskone.demo.repository.UserAccountRepository;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.utils.TicketCategoryEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class BookingFacadeIntegrationTest {
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 0;
    private static final int SEAT_NUMBER = 128;
    private static final String MONEY_AMOUNT = "1000.50";
    private static final String TICKET_PRICE = "50.25";
    private static final String ZERO_MONEY_AMOUNT = "0.0";
    private static final String EVENT_TITLE = "Test Title";
    private static final String EVENT_TITLE_1 = "Tset Eltit";
    private static final String USER_EMAIL = "john_macklein@die.hard";
    private static final String USER_EMAIL_1 = "nhoj_macklein@die.hard";
    private static final String USER_NAME = "John Macklein";
    private static final String USER_NAME_1 = "Nhoj Macklein";
    private static final LocalDate EVENT_DATE = LocalDate.of(2023, 10, 12);
    private static final LocalDate EVENT_DATE_1 = LocalDate.of(2024, 11, 9);
    private static final TicketCategoryEnum TICKET_CATEGORY = TicketCategoryEnum.PREMIUM;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookingFacade testInstance;

    @Test
    public void shouldCreateEvent() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();

        Event result = testInstance.createEvent(event);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getDate(), is(EVENT_DATE));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        Optional<Event> storedEvent = eventRepository.findById(result.getId());
        assertThat(storedEvent.isPresent(), is(Boolean.TRUE));
        assertThat(storedEvent.get().getDate(), is(EVENT_DATE));
        assertThat(storedEvent.get().getTitle(), is(EVENT_TITLE));
        eventRepository.delete(result);
    }

    @Test
    public void shouldGetEventById() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);

        Event result = testInstance.getEventById(savedEvent.getId());

        assertThat(result.getId(), is(savedEvent.getId()));
        assertThat(result.getDate(), is(EVENT_DATE));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        eventRepository.delete(result);
    }

    @Test
    public void shouldUpdateEvent() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);
        Event updateEvent = Event.builder()
                .id(savedEvent.getId())
                .date(EVENT_DATE_1)
                .title(EVENT_TITLE_1)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();

        Event result = testInstance.updateEvent(updateEvent);

        assertThat(result.getDate(), is(EVENT_DATE_1));
        assertThat(result.getTitle(), is(EVENT_TITLE_1));
        assertThat(result.getId(), is(savedEvent.getId()));
        eventRepository.delete(result);
    }

    @Test
    public void shouldDeleteEventById() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);

        boolean result = testInstance.deleteEvent(savedEvent.getId());

        assertThat(result, is(Boolean.TRUE));
        Optional<Event> storedEvent = eventRepository.findById(savedEvent.getId());
        assertThat(storedEvent.isEmpty(), is(Boolean.TRUE));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();

        User result = testInstance.createUser(user);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
        userRepository.delete(result);
    }

    @Test
    public void shouldGetUser() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        User savedUser = userRepository.save(user);

        User result = testInstance.getUserById(savedUser.getId());

        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
        userRepository.delete(result);
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        User savedUser = userRepository.save(user);
        User updateUser = User.builder()
                .id(savedUser.getId())
                .uuid(savedUser.getUuid())
                .name(USER_NAME_1)
                .email(USER_EMAIL_1)
                .build();

        User result = testInstance.updateUser(updateUser);

        assertThat(result.getName(), is(USER_NAME_1));
        assertThat(result.getEmail(), is(USER_EMAIL_1));
        userRepository.delete(result);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        User savedUser = userRepository.save(user);

        boolean result = testInstance.deleteUser(savedUser.getId());

        assertThat(result, is(Boolean.TRUE));
        Optional<User> storedUser = userRepository.findById(savedUser.getId());
        assertThat(storedUser.isEmpty(), is(Boolean.TRUE));
    }

    @Test
    public void shouldRefillUserAccount() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .amount(new BigDecimal(ZERO_MONEY_AMOUNT))
                .uuid(UUID.randomUUID())
                .build();
        UserAccount savedAccount = userAccountRepository.save(userAccount);

        boolean result = testInstance.refillUserAccount(savedAccount.getUser().getId().intValue(), MONEY_AMOUNT);

        assertThat(result, is(Boolean.TRUE));
        Optional<UserAccount> maybeAccount = userAccountRepository.findById(savedAccount.getId());
        assertThat(maybeAccount.isPresent(), is(Boolean.TRUE));
        assertThat(maybeAccount.get().getAmount().equals(new BigDecimal(MONEY_AMOUNT)), is(Boolean.TRUE));
        userAccountRepository.delete(savedAccount);
        userRepository.delete(savedAccount.getUser());
    }

    @Test
    public void shouldBookTicket() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .amount(new BigDecimal(MONEY_AMOUNT))
                .uuid(UUID.randomUUID())
                .build();
        UserAccount savedAccount = userAccountRepository.save(userAccount);
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);
        Ticket ticket = Ticket.builder()
                .bookingStatus(Boolean.FALSE)
                .user(savedAccount.getUser())
                .event(savedEvent)
                .seatNumber(SEAT_NUMBER)
                .ticketCategory(TICKET_CATEGORY)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);

        Ticket result = testInstance.bookTicket(savedAccount.getUser().getId(), savedEvent.getId(), SEAT_NUMBER, TICKET_CATEGORY);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getSeatNumber(), is(SEAT_NUMBER));
        assertThat(result.getTicketCategory(), is(TICKET_CATEGORY));
        assertThat(result.isBookingStatus(), is(Boolean.TRUE));
        ticketRepository.delete(savedTicket);
        eventRepository.delete(savedEvent);
        userAccountRepository.delete(savedAccount);
        userRepository.delete(savedAccount.getUser());
    }

    @Test
    public void shouldGetAllTicketsByEvent() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);
        Ticket ticket = Ticket.builder()
                .bookingStatus(Boolean.FALSE)
                .event(savedEvent)
                .seatNumber(SEAT_NUMBER)
                .ticketCategory(TICKET_CATEGORY)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);



        List<Ticket> result = testInstance.getBookedTickets(savedEvent, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getSeatNumber(), is(savedTicket.getSeatNumber()));
        assertThat(result.get(0).getTicketCategory(), is(TICKET_CATEGORY));
        assertThat(result.get(0).isBookingStatus(), is(Boolean.FALSE));
        assertThat(result.get(0).getEvent().getId(), is(savedEvent.getId()));
        ticketRepository.delete(savedTicket);
        eventRepository.delete(savedEvent);
    }

    @Test
    public void shouldGetAllTicketsByUser() throws Exception {
        User user = User.builder()
                .name(USER_NAME)
                .email(USER_EMAIL)
                .uuid(UUID.randomUUID())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .amount(new BigDecimal(MONEY_AMOUNT))
                .uuid(UUID.randomUUID())
                .build();
        UserAccount savedAccount = userAccountRepository.save(userAccount);
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);
        Ticket ticket = Ticket.builder()
                .bookingStatus(Boolean.TRUE)
                .user(savedAccount.getUser())
                .event(savedEvent)
                .seatNumber(SEAT_NUMBER)
                .ticketCategory(TICKET_CATEGORY)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);


        List<Ticket> result = testInstance.getBookedTickets(savedAccount.getUser(), PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getSeatNumber(), is(savedTicket.getSeatNumber()));
        assertThat(result.get(0).getTicketCategory(), is(TICKET_CATEGORY));
        assertThat(result.get(0).isBookingStatus(), is(Boolean.TRUE));
        assertThat(result.get(0).getUser().getId(), is(savedAccount.getUser().getId()));
        ticketRepository.delete(savedTicket);
        eventRepository.delete(savedEvent);
        userAccountRepository.delete(savedAccount);
        userRepository.delete(savedAccount.getUser());
    }

    @Test
    public void shouldCancelTicketById() throws Exception {
        Event event = Event.builder()
                .date(EVENT_DATE)
                .title(EVENT_TITLE)
                .ticketPrice(new BigDecimal(TICKET_PRICE))
                .build();
        Event savedEvent = eventRepository.save(event);
        Ticket ticket = Ticket.builder()
                .bookingStatus(Boolean.FALSE)
                .event(savedEvent)
                .seatNumber(SEAT_NUMBER)
                .ticketCategory(TICKET_CATEGORY)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);

        boolean result = testInstance.cancelTicket(savedTicket.getId());

        assertThat(result, is(Boolean.TRUE));
        assertThat(ticketRepository.findById(savedTicket.getId()).isEmpty(), is(Boolean.TRUE));
        eventRepository.delete(savedEvent);
    }
}
