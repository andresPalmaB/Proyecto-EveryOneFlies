package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;

public interface TicketService {

    Ticket createTicket(TicketDTO ticketDTO);

    Ticket generateTicketPdf(Long ticketId);

    Ticket updateTicket(Ticket ticket);

    Ticket getTicketById(Long idticket);
}
