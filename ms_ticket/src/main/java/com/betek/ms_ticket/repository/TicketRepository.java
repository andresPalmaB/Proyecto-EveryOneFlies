package com.betek.ms_ticket.repository;

import com.betek.ms_ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Método para encontrar tickets por idPassenger
    List<Ticket> findByIdPassenger(String idPassenger);
}
