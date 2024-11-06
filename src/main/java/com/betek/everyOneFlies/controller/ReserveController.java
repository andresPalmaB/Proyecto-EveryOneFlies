package com.betek.everyOneFlies.controller;

import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReserveController {

    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @Operation(summary = "Crea una Reserva", description = "Crea una reserva y pasajeros")
    @PostMapping
    public ResponseEntity<Reserve> createReserve(@RequestBody Reserve reserve) {
        return ResponseEntity.ok(reserveService.createReserve(reserve));
    }

    @Operation(summary = "Obtine todas las reservas", description = "Devuelve los detalles de las reservas")
    @GetMapping
    public ResponseEntity<List<Reserve>> getAllReserves() {
        return ResponseEntity.ok(reserveService.getAllReserves());
    }

    @Operation(summary = "Obtine reservas por ID", description = "Devuelve los detalles de una reserva específico")
    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(@PathVariable Long id) {
        return ResponseEntity.ok(reserveService.getReserveById(id));
    }

    @Operation(summary = "Actualiza reservas", description = "Devuelve los detalles de una reserva específico")
    @PutMapping("/{id}")
    public ResponseEntity<Reserve> updateReserve(@PathVariable Long id, @RequestBody Reserve reserve) {
        return ResponseEntity.ok(reserveService.updateReserve(id, reserve));
    }

    @Operation(summary = "Actualiza el pago de la reserva", description = "Devuelve los detalles de la reserva actualiza")
    @PutMapping("/{reservationId}/process-pay")
    public ResponseEntity<Reserve> processPayment(@PathVariable Long reservationId, @RequestBody ReservationStatus newStatus) {
        return ResponseEntity.ok(reserveService.updateReserveStatus(reservationId, newStatus));
    }

    @Operation(summary = "Crea un ticket", description = "Devuelve los detalles de un ticket específico")
    @PostMapping("/create")
    public ResponseEntity<Void> createTicket(@RequestBody TicketDTO ticketDTO) {
        reserveService.createTicket(ticketDTO);
        return ResponseEntity.ok().build(); // Respuesta 200 OK sin contenido
    }

    @Operation(summary = "Crea un archivo pdf de ticket", description = "se genera el pdf")
    @PostMapping("/{ticketId}/process")
    public ResponseEntity<Void> processTicket(@PathVariable("ticketId") Long ticketId) {
        reserveService.procesarTicket(ticketId);
        return ResponseEntity.ok().build(); // Respuesta 200 OK sin contenido
    }

    @Operation(summary = "Actualiza ticket y genera el nuevo archivo pdf", description = "se actualiza el ticket se genera el pdf")
    @PutMapping("/{ticketId}/update")
    public ResponseEntity<Void> updateTicket(@PathVariable("ticketId") Long ticketId) {
        reserveService.updateTicket(ticketId);
        return ResponseEntity.ok().build(); // Respuesta 200 OK sin contenido
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable Long id) {
        reserveService.deleteReserve(id);
        return ResponseEntity.noContent().build();
    }
}
