package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Método para encontrar tickets por reserve
    Optional<Ticket> findTicketByReserve(Reserve reserve);
}
