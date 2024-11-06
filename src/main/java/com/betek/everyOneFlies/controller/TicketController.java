package com.betek.everyOneFlies.controller;

import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.service.serviceInterface.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Generar PDF de ticket y confirmar estado
    @PutMapping("/{ticketId}/generate-pdf")
    public ResponseEntity<Ticket> generateTicketPdf(@PathVariable Long ticketId) {
        try {
            Ticket ticket = ticketService.generateTicketPdf(ticketId);
            return ResponseEntity.ok(ticket);
        } catch (ResourceNotFoundException e) {
            logger.error("Error al generar PDF: Ticket no encontrado", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error inesperado al generar PDF", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar ticket
    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        try {
            ticket.setIdTicket(ticketId);
            Ticket updatedTicket = ticketService.updateTicket(ticket);
            return ResponseEntity.ok(updatedTicket);
        } catch (ResourceNotFoundException e) {
            logger.error("Error al actualizar el ticket: No encontrado", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el ticket", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{idTicket}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("idTicket") long idTicket) {
        Ticket ticket = ticketService.getTicketById(idTicket);
        return ResponseEntity.ok(ticket);
    }
}
