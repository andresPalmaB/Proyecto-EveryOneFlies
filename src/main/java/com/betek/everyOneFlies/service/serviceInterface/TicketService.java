package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;

import java.time.LocalDateTime;

public interface TicketService {

    void createTicket(TicketDTO ticketDTO);

    byte[] generateAndValidateTicket(Long ticketId, LocalDateTime now);

    Ticket getTicketByPassenger(Passenger passenger);

    Ticket getTicketById(Long idticket);
}
