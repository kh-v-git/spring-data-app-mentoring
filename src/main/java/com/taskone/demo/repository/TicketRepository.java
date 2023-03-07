package com.taskone.demo.repository;


import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user);

    List<Ticket> findAllByUser(User user, Pageable pageable);

    List<Ticket> findAllByEvent(Event event);

    List<Ticket> findAllByEvent(Event event, Pageable pageable);

    Optional<Ticket> findTicketByEvent_IdAndSeatNumber(long eventId, int seatNumber);
}