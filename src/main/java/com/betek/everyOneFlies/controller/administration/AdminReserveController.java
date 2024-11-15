package com.betek.everyOneFlies.controller.administration;

import com.betek.everyOneFlies.dto.dtoModel.GenerateTicketDTO;
import com.betek.everyOneFlies.dto.dtoModel.ReserveDTO;
import com.betek.everyOneFlies.dto.dtoModel.UpdateReserveStatusDTO;
import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.service.serviceInterface.ReserveService;
import com.betek.everyOneFlies.service.serviceInterface.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/reserve")
@AllArgsConstructor
@Tag(name = "Reserve", description = "Endpoints for managing reservations")
public class AdminReserveController {

    @Autowired
    private final ReserveService reserveService;

    @Autowired
    private final TicketService ticketService;

    @Operation(summary = "Create new reserve", description = "Create a new reservation in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserve.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservation details.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Reservation already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<List<Reserve>> createReserve(
            @Parameter(description = "Information about the reservation to be created", required = true)
            @Valid @RequestBody ReserveDTO reserveDTO) {
        List<Reserve> reserves = reserveService.createReserve(reserveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserves);
    }

    @Operation(summary = "Get reserves by flight code", description = "Gets reservations for a specific flight using its flight code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserve.class))),
            @ApiResponse(responseCode = "404", description = "Flight with the specified code not found", content = @Content)
    })
    @GetMapping("/byFlight/{flightCode}")
    public ResponseEntity<List<Reserve>> getReservesByFlightCode(
            @Parameter(description = "Flight code of the flight to search for reservations", required = true)
            @PathVariable String flightCode) {
        List<Reserve> reserves = reserveService.getReservesFromFlight(flightCode);
        return ResponseEntity.ok(reserves);
    }

    @Operation(summary = "Get reserve by ID", description = "Gets the details of a reservation using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserve.class))),
            @ApiResponse(responseCode = "404", description = "Reservation with the specified ID not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(
            @Parameter(description = "ID of the reservation to search for", required = true)
            @PathVariable Long id) {
        Reserve reserve = reserveService.getReserveById(id);
        return ResponseEntity.ok(reserve);
    }

    @Operation(summary = "Get reserves by reservation code", description = "Gets reservations using the reservation code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserve.class))),
            @ApiResponse(responseCode = "404", description = "Reservation with the specified code not found", content = @Content)
    })
    @GetMapping("/byCode/{reserveCode}")
    public ResponseEntity<List<Reserve>> getReservesByCode(
            @Parameter(description = "Reservation code to search for", required = true)
            @PathVariable String reserveCode) {
        List<Reserve> reserves = reserveService.getReservesByReserveCode(reserveCode);
        return ResponseEntity.ok(reserves);
    }

    @Operation(summary = "Update reservation status", description = "Updates the status of reservations using the reservation code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation status updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserve.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status provided.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)
    })
    @PutMapping("/updateStatus")
    public ResponseEntity<List<Reserve>> updateReserveStatus(
            @Parameter(description = "Update details for reservation status.", required = true)
            @RequestBody UpdateReserveStatusDTO updateReserveStatusDTO) {
        List<Reserve> updatedReserves = reserveService.updateReserveStatus(
                updateReserveStatusDTO.reserveCode().toLowerCase(),
                updateReserveStatusDTO.newStatus().toUpperCase(),
                updateReserveStatusDTO.now()
        );
        return ResponseEntity.ok(updatedReserves);
    }

    @Operation(summary = "Generate and Download Ticket",
            description = "Generates a ticket in PDF format and downloads it directly in the browser. Current date and time in format yyyy-MM-dd HH:mm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket generated successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "404", description = "Ticket not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid ticket or validation failed", content = @Content)
    })
    @PostMapping("/generateTicket")
    public ResponseEntity<byte[]> generateTicketFromReserve(
            @Parameter(description = "Ticket id of the reservation to generate Ticket", required = true)
            @Valid @RequestBody GenerateTicketDTO generateTicketDTO) {

        byte[] pdfBytes = ticketService.generateAndValidateTicket(generateTicketDTO.idTicket(), generateTicketDTO.now());

        Ticket ticket = ticketService.getTicketById(generateTicketDTO.idTicket());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("ticket_" + ticket.generateNameTicket() + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}

