package com.betek.ms_files;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class Ticket_Manager {
    private List<Ticket> tickets = new ArrayList<>();

    public Ticket generateTicket(String idPassenger, String flightInfo) {
        Ticket ticket = new Ticket();
        ticket.setIdPassenger(idPassenger);
        ticket.setFlightInfo(flightInfo);
        ticket.setIssueDate(getCurrentDate());
        ticket.setTicketStatus(TicketStatus.CONFIRMADO); // Usando enum para estados

        tickets.add(ticket);
        return ticket;
    }

    public boolean checkInTicket(Long idTicket) {
        Ticket ticket = searchTicketById(idTicket);
        if (ticket != null && ticket.getTicketStatus() == TicketStatus.CONFIRMADO) {
            ticket.setTicketStatus(TicketStatus.CHECK_IN);
            // Aquí podrías llamar a un método para generar el pase de abordar en PDF
            return true;
        }
        return false;
    }

    public Ticket searchTicketById(Long idTicket) {
        for (Ticket ticket : tickets) {
            if (ticket.getIdTicket().equals(idTicket)) {
                return ticket;
            }
        }
        return null; // Ticket no encontrado
    }

    private LocalDate getCurrentDate() {
        return LocalDate.now(); // Obtener la fecha actual
    }

    public void displayTickets() {
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public boolean cancelTicket(Long idTicket) {
        Ticket ticket = searchTicketById(idTicket);
        if (ticket != null && ticket.getTicketStatus() != TicketStatus.CHECK_IN) {
            ticket.setTicketStatus(TicketStatus.CANCELADO);
            return true;
        }
        return false; // No se puede cancelar
    }
}
