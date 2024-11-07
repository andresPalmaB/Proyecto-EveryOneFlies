package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.model.Reserve;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "ID_PASSENGER", nullable = false)
    @NotNull(message = "Passenger is mandatory")
    private Passenger passenger;

    @Column(name = "ISSUE_DATE", nullable = false)
    @NotNull(message = "Issue date is mandatory")
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TICKET_STATUS", nullable = false)
    @NotNull(message = "TicketStatus is mandatory")
    private TicketStatus ticketStatus;

    public Ticket(Passenger passenger, LocalDate issueDate, TicketStatus ticketStatus) {
        this.passenger = passenger;
        this.issueDate = issueDate;
        this.ticketStatus = ticketStatus;
    }
}