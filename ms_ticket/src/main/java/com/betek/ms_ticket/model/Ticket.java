package com.betek.ms_ticket.model;

import com.betek.ms_ticket.model.modelEnum.TicketStatus;
import com.ms_reserve.reserve.model.Passenger;
import com.ms_reserve.reserve.model.Reserve;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket; // Cambiado a Long
    private Passenger passenger;
    private Reserve reserve;
    private LocalDate issueDate; // Usando LocalDate
    private TicketStatus ticketStatus; // Usando enum

}