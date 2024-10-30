package com.betek.ms_flies.controller;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.service.serviceInterface.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody LocationDTO locationDTO) {
        Location createdLocation = locationService.createLocation(locationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Integer id) {
        Location location = locationService.getLocationById(id);
        return ResponseEntity.ok(location);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Location> getLocationByCity(@PathVariable String city) {
        Location location = locationService.getLocationByCity(city);
        return ResponseEntity.ok(location);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Location>> getLocationsByCountry(@PathVariable String country) {
        List<Location> locations = locationService.getLocationByCountry(country);
        return ResponseEntity.ok(locations);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return ResponseEntity.ok(locations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse<?>> deleteLocationById(@PathVariable Integer id) {
        Location location = new Location();
        location.setIdLocation(id);
        DeleteResponse<?> deleteResponse = locationService.deleteLocationById(location);
        return ResponseEntity.ok(deleteResponse);
    }
}
