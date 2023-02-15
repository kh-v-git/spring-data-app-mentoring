package com.taskone.demo.service.ticket;


import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.repository.EventRepository;
import com.taskone.demo.repository.TicketRepository;
import com.taskone.demo.repository.UserRepository;
import com.taskone.demo.utils.TicketCategoryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, TicketCategoryEnum ticketCategory) {

        if (userRepository.existsById(userId) && eventRepository.existsById(eventId)) {
            Ticket ticket = Ticket.builder()
                    .seatNumber(place)
                    .ticketCategory(ticketCategory)
                    .user(userRepository.findById(userId).orElseThrow())
                    .event(eventRepository.findById(eventId).orElseThrow())
                    .build();

            Ticket savedTicket = ticketRepository.save(ticket);

            log.debug("New Ticket with ID {} was created.", savedTicket.getId());

            return savedTicket;
        } else {
            log.debug("New Ticket not created with userId = {}, eventId = {}, place = {}, ticketCategory = {}", userId, eventId, place, ticketCategory);

            return null;
        }
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        return ticketRepository.findAllByUser(user);
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        return ticketRepository.findAllByEvent(event);
    }

    @Override
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
