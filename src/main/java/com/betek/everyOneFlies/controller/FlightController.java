package com.betek.everyOneFlies.controller;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.OutBoundRoute;
import com.betek.everyOneFlies.dto.ReturnRoute;
import com.betek.everyOneFlies.dto.dtoModel.FlightDTO;
import com.betek.everyOneFlies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.model.modelEnum.TipoAsiento;
import com.betek.everyOneFlies.service.serviceInterface.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@AllArgsConstructor
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDTO flightDTO) {
        Flight createdFlight = flightService.createFlight(flightDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @GetMapping("/id/{idFlight}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long idFlight) {
        Flight flight = flightService.getFlightByIdFlight(idFlight);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/flightCode/{flightCode}")
    public ResponseEntity<Flight> getFlightByFlightCode(@PathVariable String flightCode) {
        Flight flight = flightService.getFlightByFlightCode(flightCode);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/route")
    public ResponseEntity<List<Flight>> getRouteByDate(@RequestBody OutBoundRoute route) {
        List<Flight> flights = flightService.getRouteByDate(route);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/range")
    public ResponseEntity<List<Flight>> getRutaByRangoFechas(@RequestBody ReturnRoute route) {
        List<Flight> flights = flightService.getRutaByRangoFechas(route);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/seats/availablebytype")
    public ResponseEntity<List<Seat>> getAvailableSeatsByType(@RequestBody SeatDTO seatDTO) {
        List<Seat> availableSeats = flightService.getSeatAvailableBySeatTypeFromFlight(seatDTO);
        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/seats/available/{flightCode}")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable String flightCode) {
        List<Seat> availableSeats = flightService.getSeatAvailableFromFlight(flightCode);
        return ResponseEntity.ok(availableSeats);
    }

    @GetMapping("/cost/{flightCode}")
    public ResponseEntity<Double> getFlightCost(@PathVariable String flightCode, @RequestParam TipoAsiento tipo) {
        Double cost = flightService.getFlightCost(tipo, flightCode);
        return ResponseEntity.ok(cost);
    }

    @PutMapping
    public ResponseEntity<Flight> updateFlightDateTime(@RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlightDateTime(flight);
        return ResponseEntity.ok(updatedFlight);
    }

    @PutMapping("/seats/sold")
    public ResponseEntity<Flight> updateSeatsSoldFlight(@RequestBody FlightSeatSoldDTO seatsSoldDTO) {
        Flight updatedFlight = flightService.updateSeatsSoldFlight(seatsSoldDTO);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/delete/{flightCode}")
    public ResponseEntity<DeleteResponse<Flight>> deleteFlightById(@PathVariable String flightCode) {
        Flight flight = flightService.getFlightByFlightCode(flightCode);
        DeleteResponse<Flight> response = flightService.deleteFlightById(flight);
        return ResponseEntity.ok(response);
    }
}
