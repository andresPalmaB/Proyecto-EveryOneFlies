package com.betek.ms_flies.controller;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirlineDTO;
import com.betek.ms_flies.model.Airline;
import com.betek.ms_flies.service.serviceInterface.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@AllArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<Airline> createAirline(@RequestBody AirlineDTO airlineDTO) {
        Airline createdAirline = airlineService.createAirline(airlineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirline);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Integer id) {
        Airline airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }

    @GetMapping
    public ResponseEntity<List<Airline>> getAirlines() {
        List<Airline> airlines = airlineService.getAirlines();
        return ResponseEntity.ok(airlines);
    }

    @GetMapping("/name")
    public ResponseEntity<Airline> getAirlineByName(@RequestBody AirlineDTO airlineDTO) {
        Airline airline = airlineService.getAirlineByName(airlineDTO);
        return ResponseEntity.ok(airline);
    }

    @PutMapping("/update")
    public ResponseEntity<Airline> updateAirline(@RequestBody Airline updatedAirline) {
        Airline airline = airlineService.updateAirline(updatedAirline);
        return ResponseEntity.ok(airline);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteResponse<?>> deleteAirlineById(@PathVariable Integer id) {
        Airline airline = new Airline();
        airline.setIdAirline(id);
        DeleteResponse<?> deleteResponse = airlineService.deleteAirlineById(airline);
        return ResponseEntity.ok(deleteResponse);
    }
}
