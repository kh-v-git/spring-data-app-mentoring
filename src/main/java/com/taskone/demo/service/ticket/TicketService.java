package com.taskone.demo.service.ticket;


import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.utils.TicketCategoryEnum;
import com.taskone.demo.utils.exception.BookingServiceException;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(long userId, long eventId, int place, TicketCategoryEnum ticketCategory) throws BookingServiceException;

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) throws BookingServiceException;

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) throws BookingServiceException;

    boolean cancelTicket(long ticketId);
}
