package com.taskone.demo.domain;

import com.taskone.demo.utils.TicketCategoryEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Ticket")
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    @NotNull(message = "Ticket must have a seatNumber")
    private int seatNumber;

    @Column(name = "ticket_category")
    @NotNull(message = "Ticket must have a category")
    @Enumerated(EnumType.STRING)
    private TicketCategoryEnum ticketCategory;

    @Column
    @NotNull(message = "Ticket must have status")
    private boolean bookingStatus;


    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Ticket must have an Event")
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

}
