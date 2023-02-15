package com.taskone.demo.repository;


import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUser(User user);

    List<Ticket> findAllByEvent(Event event);
}
