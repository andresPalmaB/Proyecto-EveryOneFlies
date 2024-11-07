package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.model.Reserve;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TICKET")
    private Long idTicket;

    @ManyToOne
    @JoinColumn(name = "ID_PASAJERO", nullable = false)
    private Passenger passenger;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus ticketStatus;

    public Ticket(Passenger passenger, LocalDate issueDate, TicketStatus ticketStatus) {
        this.passenger = passenger;
        this.issueDate = issueDate;
        this.ticketStatus = ticketStatus;
    }
}