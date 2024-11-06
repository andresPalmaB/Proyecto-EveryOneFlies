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
    @Column(name = "ticket_id")
    private Long idTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reserve reserve;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus ticketStatus;

    public Ticket(Reserve reserve, LocalDate issueDate, TicketStatus ticketStatus) {
        this.reserve = reserve;
        this.issueDate = issueDate;
        this.ticketStatus = ticketStatus;
    }
}