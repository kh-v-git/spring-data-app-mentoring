package com.taskone.demo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Event")
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Event must have a Date")
    @Column(name = "date")
    private LocalDate date;

    @NotNull(message = "Event must have a Title")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Event must have a Price")
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private List<Ticket> ticket;
}
