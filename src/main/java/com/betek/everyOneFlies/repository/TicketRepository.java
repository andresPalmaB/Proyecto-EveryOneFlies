package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findTicketByPassenger(Passenger passenger);

}
