package com.betek.everyOneFlies.controller.administration;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.service.serviceInterface.AirportService;
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
@RequestMapping("api/admin/airport")
@AllArgsConstructor
@Tag(name = "Airport", description = "Administration-related endpoints")
public class AdminAirportController {

    @Autowired
    private final AirportService airportService;

    @Operation(summary = "Create new airport", description = "Create a new airport in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "400", description = "Airport fields are empty, null, or exceed the number of characters.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Location by the specified city not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Airport already exists in the database",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<Airport> createAirport(
            @Parameter(description = "Information about the airport to be created", required = true)
            @Valid @RequestBody AirportDTO airportDTO) {
        Airport airport = airportService.createAirport(airportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(airport);
    }

    @Operation(summary = "Get airport by ID", description = "Gets the details of an airport using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "404", description = "Airport with the specified ID not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(
            @Parameter(description = "ID of the airport to search for", required = true)
            @PathVariable Integer id) {
        Airport airport = airportService.getAirportById(id);
        return ResponseEntity.ok(airport);
    }

    @Operation(summary = "Get all airports", description = "Gets a list of all airports in the database.")
    @ApiResponse(responseCode = "200", description = "List of airports found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Airport.class)))
    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportService.getAirports();
        return ResponseEntity.ok(airports);
    }

    @Operation(summary = "Get airport by IATA Code", description = "Gets the details of an airport using its IATA Code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "404", description = "Airport with the IATA Code not found", content = @Content)
    })
    @GetMapping("/byIata/{iataCode}")
    public ResponseEntity<Airport> getAirportByIataCode(
            @Parameter(description = "IATA code of the airport to search for", required = true)
            @PathVariable String iataCode) {
        Airport airport = airportService.getAirportByIataCode(iataCode);
        return ResponseEntity.ok(airport);
    }

    @Operation(summary = "Get airport by name", description = "Gets the details of an airport using its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "404", description = "Airport with the specified name not found", content = @Content)
    })
    @GetMapping("/byName/{name}")
    public ResponseEntity<Airport> getAirportByName(
            @Parameter(description = "Name of the airport to search for", required = true, example = "International Airport")
            @PathVariable String name) {
        Airport airport = airportService.getAirportByName(name);
        return ResponseEntity.ok(airport);
    }

    @Operation(summary = "Get airport by city", description = "Gets a list of airports in the given city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of airports found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
    })
    @GetMapping("/byCity/{city}")
    public ResponseEntity<List<Airport>> getAirportsByCity(
            @Parameter(description = "City to search for airports", required = true)
            @PathVariable String city) {
        List<Airport> airports = airportService.getAirportByCity(city);
        return ResponseEntity.ok(airports);
    }

    @Operation(summary = "Get airport by country", description = "Gets a list of airports in the given country.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of airports found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
    })
    @GetMapping("/byCountry/{country}")
    public ResponseEntity<List<Airport>> getAirportsByCountry(
            @Parameter(description = "City to search for airports", required = true)
            @PathVariable String country) {
        List<Airport> airports = airportService.getAirportsByCountry(country);
        return ResponseEntity.ok(airports);
    }

    @Operation(summary = "Update airport", description = "Updates the details of an existing airport.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airport.class))),
            @ApiResponse(responseCode = "404", description = "Airport not found", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<Airport> updateAirport(
            @Parameter(description = "Airport with updated details", required = true)
            @RequestBody Airport airport) {
        Airport updatedAirport = airportService.updateAirport(airport);
        return ResponseEntity.ok(updatedAirport);
    }

    @Operation(summary = "Delete airport", description = "Deletes an airport from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Airport not found or mismatch in data", content = @Content)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse<String>> deleteAirport(
            @Parameter(description = "Airport to delete", required = true)
            @RequestBody Airport airport) {
        DeleteResponse<String> deleteResponse = airportService.deleteAirport(airport);
        return ResponseEntity.ok(deleteResponse);
    }

}
