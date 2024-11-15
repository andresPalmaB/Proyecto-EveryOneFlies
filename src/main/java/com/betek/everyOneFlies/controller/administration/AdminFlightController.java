package com.betek.everyOneFlies.controller.administration;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.OutBoundRoute;
import com.betek.everyOneFlies.dto.ReturnRoute;
import com.betek.everyOneFlies.dto.dtoModel.FlightDTO;
import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.exception.ValidateArguments;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;
import com.betek.everyOneFlies.service.serviceInterface.FlightService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/flight")
@AllArgsConstructor
@Tag(name = "Flight", description = "Administration-related endpoints for flights")
public class AdminFlightController {

    @Autowired
    private final FlightService flightService;

    @Operation(summary = "Create new flight", description = "Create a new flight in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "400", description = "Flight fields are empty, null, or incorrect.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Flight already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(
            @Parameter(description = "Information about the flight to be created", required = true)
            @Valid @RequestBody FlightDTO flightDTO) {
        Flight flight = flightService.createFlight(flightDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @Operation(summary = "Get flight by ID", description = "Gets the details of a flight using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Flight with the specified ID not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(
            @Parameter(description = "ID of the flight to search for", required = true)
            @PathVariable Long id) {
        Flight flight = flightService.getFlightByIdFlight(id);
        return ResponseEntity.ok(flight);
    }

    @Operation(summary = "Get flight by flight code", description = "Gets the details of a flight using its flight code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Flight with the specified code not found", content = @Content)
    })
    @GetMapping("/byCode/{flightCode}")
    public ResponseEntity<Flight> getFlightByCode(
            @Parameter(description = "Flight code of the flight to search for", required = true)
            @PathVariable String flightCode) {
        Flight flight = flightService.getFlightByFlightCode(flightCode.toLowerCase());
        return ResponseEntity.ok(flight);
    }

    @Operation(summary = "Get flights by route and date", description = "Gets flights based on the given route and departure date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flights found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "No flights found for the given route and date", content = @Content)
    })
    @GetMapping("/routeByDate")
    public ResponseEntity<List<Flight>> getRouteByDate(
            @Parameter(description = "Origin IATA code", required = true)
            @RequestParam String originIataCode,
            @Parameter(description = "Destination IATA code", required = true)
            @RequestParam String destinationIataCode,
            @Parameter(description = "Departure date", required = true)
            @RequestParam String departureDate) {
        OutBoundRoute route = new OutBoundRoute(departureDate, originIataCode.toLowerCase(), destinationIataCode.toLowerCase());
        List<Flight> flights = flightService.getRouteByDate(route);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get flights by route and date range", description = "Gets flights based on the given route and departure/return date range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flights found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "No flights found for the given route and date range", content = @Content)
    })
    @GetMapping("/routeByDateRange")
    public ResponseEntity<List<Flight>> getRouteByDateRange(
            @Parameter(description = "Origin IATA code", required = true)
            @RequestParam String originIataCode,
            @Parameter(description = "Destination IATA code", required = true)
            @RequestParam String destinationIataCode,
            @Parameter(description = "Departure date", required = true)
            @RequestParam String departureDate,
            @Parameter(description = "Return date", required = true)
            @RequestParam String returnDate) {
        ReturnRoute route = new ReturnRoute(departureDate, returnDate, originIataCode, destinationIataCode);
        List<Flight> flights = flightService.getRutaByRangoFechas(route);
        return ResponseEntity.ok(flights);
    }

    @Operation(summary = "Get available seats by type", description = "Gets available seats by type for a specific flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available seats found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seat.class))),
            @ApiResponse(responseCode = "400", description = "Invalid seat category.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/availableSeats")
    public ResponseEntity<List<Seat>> getAvailableSeatsByType(
            @Parameter(description = "Flight code", required = true)
            @RequestParam String flightCode,
            @Parameter(description = "Seat category", required = true)
            @RequestParam String seatCategory) {
        SeatCategory seatCategoryEnum = ValidateArguments.validateSeatCategory(seatCategory.toUpperCase());
        SeatDTO seatDTO = new SeatDTO(flightCode.toLowerCase(), seatCategoryEnum.name());
        List<Seat> availableSeats = flightService.getSeatAvailableBySeatTypeFromFlight(seatDTO);
        return ResponseEntity.ok(availableSeats);
    }

    @Operation(summary = "Get all available seats", description = "Gets all available seats for a specific flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available seats found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seat.class)))
    })
    @GetMapping("/allAvailableSeats/{flightCode}")
    public ResponseEntity<List<Seat>> getAllAvailableSeats(
            @Parameter(description = "Flight code of the flight to get available seats", required = true)
            @PathVariable String flightCode) {
        List<Seat> availableSeats = flightService.getSeatAvailableFromFlight(flightCode);
        return ResponseEntity.ok(availableSeats);
    }

    @Operation(summary = "Get flight cost", description = "Gets the cost of a flight based on seat type and other details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight cost calculated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "400", description = "Invalid seat category",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/cost")
    public ResponseEntity<Double> getFlightCost(
            @Parameter(description = "Flight code of the flight", required = true)
            @RequestParam String flightCode,
            @Parameter(description = "Seat category", required = true)
            @RequestParam String seatCategory) {
        SeatCategory seatCategoryEnum = SeatCategory.valueOf(seatCategory.toUpperCase());
        SeatDTO seatDTO = new SeatDTO(flightCode.toLowerCase(), seatCategoryEnum.name());
        Double cost = flightService.getFlightCost(seatDTO);
        return ResponseEntity.ok(cost);
    }

    @Operation(summary = "Update flight date and time", description = "Updates the departure and arrival date/time of a flight.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Flight.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found", content = @Content)
    })
    @PutMapping("/updateDateTime")
    public ResponseEntity<Flight> updateFlightDateTime(
            @Parameter(description = "Flight with updated date/time details", required = true)
            @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlightDateTime(flight);
        return ResponseEntity.ok(updatedFlight);
    }

    @Operation(summary = "Delete flight by ID", description = "Deletes a flight from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found or mismatch in data", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse<String>> deleteFlight(
            @Parameter(description = "Flight to delete", required = true)
            @RequestBody Flight flight) {
        DeleteResponse<String> deleteResponse = flightService.deleteFlightById(flight);
        return ResponseEntity.ok(deleteResponse);
    }

}
