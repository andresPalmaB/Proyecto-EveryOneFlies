package com.betek.everyOneFlies.controller;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.service.serviceInterface.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Crea una Aerolinea", description = "Devuelve los detalles de una Arolinea específico")
    @PostMapping
    public ResponseEntity<Airline> createAirline(@RequestBody AirlineDTO airlineDTO) {
        Airline createdAirline = airlineService.createAirline(airlineDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirline);
    }

    @Operation(summary = "Obtine Aerolinea por ID", description = "Devuelve los detalles de una Arolinea específico")
    @GetMapping("/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Integer id) {
        Airline airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }

    @Operation(summary = "Obtine Aerolineas", description = "Devuelve los detalles de todas Arolineas")
    @GetMapping
    public ResponseEntity<List<Airline>> getAirlines() {
        List<Airline> airlines = airlineService.getAirlines();
        return ResponseEntity.ok(airlines);
    }

    @Operation(summary = "Obtine Aerolineas por nombre", description = "Devuelve los detalles de una Arolinea específico")
    @GetMapping("/name")
    public ResponseEntity<Airline> getAirlineByName(@RequestBody AirlineDTO airlineDTO) {
        Airline airline = airlineService.getAirlineByName(airlineDTO);
        return ResponseEntity.ok(airline);
    }

    @Operation(summary = "Actualiza Aerolineas", description = "Devuelve los detalles de una Arolinea específico")
    @PutMapping("/update")
    public ResponseEntity<Airline> updateAirline(@RequestBody Airline updatedAirline) {
        Airline airline = airlineService.updateAirline(updatedAirline);
        return ResponseEntity.ok(airline);
    }

    @Operation(summary = "Elimina Aerolineas", description = "Verifica la eliminacion")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteResponse<?>> deleteAirlineById(@PathVariable Integer id) {
        Airline airline = new Airline();
        airline.setIdAirline(id);
        DeleteResponse<?> deleteResponse = airlineService.deleteAirlineById(airline);
        return ResponseEntity.ok(deleteResponse);
    }
}
