package com.taskone.demo.service.ticket;


import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.domain.UserAccount;
import com.taskone.demo.repository.EventRepository;
import com.taskone.demo.repository.TicketRepository;
import com.taskone.demo.repository.UserAccountRepository;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.utils.TicketCategoryEnum;
import com.taskone.demo.utils.exception.BookingServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, TicketCategoryEnum ticketCategory) throws BookingServiceException {
        User user = userRepository.findById(userId).orElseThrow(() -> new BookingServiceException(String.format("User not found with id: %s.", userId)));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new BookingServiceException(String.format("Event not found with id: %s.", eventId)));
        Ticket rawTicket = ticketRepository.findTicketByEvent_IdAndSeatNumber(eventId, place).orElseThrow(() -> new BookingServiceException(String.format("Ticket not found for  EventId: %s and Place: %s", eventId, place)));

        if (rawTicket.isBookingStatus()) {
            throw new BookingServiceException(String.format("Ticket has been booked for EventId: %s and Place: %s", eventId, place));
        }

        UserAccount userAccount = userAccountRepository.findUserAccountByUserId(userId).orElseThrow(() -> new BookingServiceException(String.format("UserAccount not found for User with id: %s.", userId)));

        if (userAccount.getAmount().subtract(event.getTicketPrice()).compareTo(BigDecimal.ZERO) < 0) {
            throw new BookingServiceException(String.format("Insufficient funds on UserAccount with id: %s.", userAccount.getId()));
        }

        userAccount.setAmount(userAccount.getAmount().subtract(event.getTicketPrice()));
        userAccountRepository.save(userAccount);

        rawTicket.setBookingStatus(Boolean.TRUE);
        rawTicket.setUser(user);
        ticketRepository.save(rawTicket);

        return rawTicket;
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) throws BookingServiceException {
        User storedUser = userRepository.findById(user.getId()).orElseThrow(() -> new BookingServiceException(String.format("User not found with id: %s.", user.getId())));
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return ticketRepository.findAllByUser(storedUser, pageable);
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) throws BookingServiceException {
        Event storedEvent = eventRepository.findById(event.getId()).orElseThrow(() -> new BookingServiceException(String.format("Event not found with id: %s.", event.getId())));
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        return ticketRepository.findAllByEvent(storedEvent, pageable);
    }

    @Override
    @Transactional
    public boolean cancelTicket(final long ticketId) {
        Optional<Ticket> maybeTicket = ticketRepository.findById(ticketId);
        if (maybeTicket.isEmpty()) {
            log.debug("Ticket with ID {} was NOT deleted cause NOT found.", ticketId);

            return false;
        }
        ticketRepository.delete(maybeTicket.get());

        log.debug("Ticket with ID {} was deleted.", ticketId);

        return true;
    }
}
