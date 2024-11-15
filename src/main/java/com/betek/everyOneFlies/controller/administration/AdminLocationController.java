package com.betek.everyOneFlies.controller.administration;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.LocationDTO;
import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.service.LocationServiceImpl;
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
@RequestMapping("api/admin/location")
@AllArgsConstructor
public class AdminLocationController {

    @Autowired
    private final LocationServiceImpl locationService;

    @Operation(summary = "Create a new location", description = "Creates a new location entry in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "400", description = "Location fields are empty, null, exceeds the number of characters or past date.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Location field already exist data base",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<Location> createLocation(
            @Parameter(description = "DTO containing the details of the new location", required = true)
            @Valid @RequestBody LocationDTO locationDTO) {
        Location location = locationService.createLocation(locationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @Operation(summary = "Get location by ID", description = "Fetches a location from the database using its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "Location with the specified ID not found", content = @Content)
    })
    @GetMapping("/{id}")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<Location> getLocationById(
            @Parameter(description = "ID of the location to be retrieved", required = true, example = "1")
            @PathVariable Integer id) {
        Location location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get location by city", description = "Fetches a location from the database based on the city name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "Location by the specified city not found", content = @Content)
    })
    @GetMapping("/ByCity/{city}")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<Location> getLocationByCity(
            @Parameter(description = "City name to search for", required = true, example = "BOGOTA")
            @PathVariable String city) {
        Location location = locationService.getLocationByCity(city);
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get location by country", description = "Fetches a list of locations based on the country name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "Location by the specified country not found", content = @Content)
    })
    @GetMapping("/ByCountry/{country}")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<List<Location>> getLocationByCountry(
            @Parameter(description = "Country name to search for", required = true, example = "COLOMBIA")
            @PathVariable String country) {
        List<Location> locations = locationService.getLocationByCountry(country);
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Get all locations", description = "Fetches all locations from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all locations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
    })
    @GetMapping("/locations")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Update Location Date", description = "Updates the dates of an existing Location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location date updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "400", description = "Location fields are empty, null, exceeds the number of characters or past date.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @PutMapping("/updateDate")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<Location> updateLocationDate(
            @Parameter(description = "Location with updated dates", required = true)
            @RequestBody Location location) {
        Location updateLocationDate = locationService.updateLocationDate(location);
        return ResponseEntity.ok(updateLocationDate);
    }

    @Operation(summary = "Update Location Base Price", description = "Updates the dates of an existing Location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location date updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Airline.class))),
            @ApiResponse(responseCode = "400", description = "Location fields are empty, null, exceeds the number of characters or past date.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @PutMapping("/updateBasePrice")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<Location> updateLocationBasePrice(
            @Parameter(description = "Location with updated dates", required = true)
            @RequestBody Location location) {
        Location updatedLocationBasePrice = locationService.updateLocationBasePrice(location);
        return ResponseEntity.ok(updatedLocationBasePrice);
    }

    @Operation(summary = "Delete a location", description = "Deletes a location entry from the database by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found and delete",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "Location not found or mismatch in data", content = @Content)
    })
    @DeleteMapping("/delete")
    @Tag(name = "Location", description = "Administration-related endpoints")
    public ResponseEntity<DeleteResponse<Location>> deleteLocation(
            @Parameter(description = "Location data to delete", required = true)
            @RequestBody Location location) {
        DeleteResponse<Location> response = locationService.deleteLocation(location);
        return ResponseEntity.ok(response);
    }

}
