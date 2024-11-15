package com.betek.everyOneFlies.controller.administration;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.service.serviceInterface.AirlineService;
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
@RequestMapping("api/admin/airline")
@AllArgsConstructor
public class AdminAirlineController {

    @Autowired
    private final AirlineService airlineService;

    @Operation(summary = "Create new airline", description = "Create new airline in the data base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airline created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "400", description = "Airline fields are empty, null or exceeds the number of characters.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Airline field already exist data base",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<Airline> createAirline(
            @Parameter(description = "Information about the airline to be created", required = true)
            @Valid @RequestBody AirlineDTO airlineDTO) {
        Airline airline = airlineService.createAirline(airlineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(airline);
    }

    @Operation(summary = "Get airline by ID", description = "Gets the details of an airline using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "404", description = "Airline with the specified ID not found", content = @Content)
    })
    @GetMapping("/{id}")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<Airline> getAirlineById(
            @Parameter(description = "ID of the airline to search for", required = true)
            @PathVariable Integer id) {
        Airline airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }

    @Operation(summary = "Get all airlines", description = "Gets a list of all airlines in the database.")
    @ApiResponse(responseCode = "200", description = "List of airlines found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Airline.class)))
    @GetMapping("/airlines")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<List<Airline>> getAllAirlines() {
        List<Airline> airlines = airlineService.getAirlines();
        return ResponseEntity.ok(airlines);
    }

    @Operation(summary = "Get airline by name", description = "Gets the details of an airline using its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "404", description = "Airline with the specified name not found", content = @Content)
    })
    @GetMapping("/byName/{name}")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<Airline> getAirlineByName(
            @Parameter(description = "Name of the airline to search for", required = true, example = "Every One Flies")
            @PathVariable String name) {
        Airline airline = airlineService.getAirlineByName(name);
        return ResponseEntity.ok(airline);
    }

    @Operation(summary = "Update Airline", description = "Updates the details of an existing airline.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "404", description = "Airline not found", content = @Content)
    })
    @PutMapping("/update")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<Airline> updateAirline(
            @Parameter(description = "Airline with updated details", required = true)
            @RequestBody Airline airline) {
        Airline updatedAirline = airlineService.updateAirline(airline);
        return ResponseEntity.ok(updatedAirline);
    }

    @Operation(summary = "Delete Airline", description = "Deletes an airline from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airline deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Airline not found or mismatch in data", content = @Content)
    })
    @DeleteMapping("/delete")
    @Tag(name = "Airline", description = "Administration-related endpoints")
    public ResponseEntity<DeleteResponse<String>> deleteAirline(
            @Parameter(description = "Airline to delete", required = true)
            @RequestBody Airline airline) {
        DeleteResponse<String> deleteResponse = airlineService.deleteAirlineById(airline);
        return ResponseEntity.ok(deleteResponse);
    }

}
