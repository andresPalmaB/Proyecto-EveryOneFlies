package com.betek.ms_flies.controller;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirportDTO;
import com.betek.ms_flies.model.Airline;
import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.service.serviceInterface.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@AllArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody AirportDTO airportDTO) {
        Airport createdAirport = airportService.createAirport(airportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Integer id) {
        Airport airport = airportService.getAirportById(id);
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/iataCode")
    public ResponseEntity<Airport> getAirportByIataCode(@RequestBody AirportDTO airportDTO) {
        Airport airport = airportService.getAirportByIataCode(airportDTO);
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/name")
    public ResponseEntity<Airport> getAirportByName(@RequestBody AirportDTO airportDTO) {
        Airport airport = airportService.getAirportByName(airportDTO);
        return ResponseEntity.ok(airport);
    }

    @GetMapping("/city")
    public ResponseEntity<List<Airport>> getAirportsByCity(@RequestBody AirportDTO airportDTO) {
        List<Airport> airports = airportService.getAirportByCity(airportDTO);
        return ResponseEntity.ok(airports);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Airport>> getAirportsByCountry(@PathVariable String country) {
        List<Airport> airports = airportService.getAirportsByCountry(country);
        return ResponseEntity.ok(airports);
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAirports() {
        List<Airport> airports = airportService.getAirports();
        return ResponseEntity.ok(airports);
    }

    @PutMapping("/update")
    public ResponseEntity<Airport> updateAirportByIataCode(@RequestBody Airport updatedAirport) {
        Airport airport = airportService.updateAirportByIataCode(updatedAirport);
        return ResponseEntity.ok(airport);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteResponse<?>> deleteAirportByIataCode(@PathVariable Integer id) {
        Airport airport = new Airport();
        airport.setIdAirport(id);
        DeleteResponse<?> deleteResponse = airportService.deleteAirportByIataCode(airport);
        return ResponseEntity.ok(deleteResponse);
    }
}

