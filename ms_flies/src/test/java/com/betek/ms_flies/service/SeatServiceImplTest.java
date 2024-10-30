package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.dtoModel.SeatDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;
import com.betek.ms_flies.repository.SeatRepository;
import com.betek.ms_flies.service.serviceInterface.FlightService;
import com.betek.ms_flies.service.serviceInterface.SeatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SeatServiceImplTest {

    @Autowired
    private SeatService service;

    @Autowired
    private SeatRepository repository;

    @Autowired
    private FlightService flightService;

    @Test
    void getSeats_shouldGetSeatByAvailabilityAndGetSeatByAvailabilityAndSeatType(){

        Flight flight = flightService.getFlightByFlightCode("eof1");

        SeatDTO seatDTO = new SeatDTO(
                flight.getFlightCode(),
                true,
                TipoAsiento.ECONOMYC
        );

        assertEquals(
                repository.findSeatByFlightAndAvailable(flight, true),
                service.getSeatAvailabilityInFlight(flight)
        );

        assertEquals(
                repository.findSeatByFlightAndTipoAsientoAndAvailable(flight, TipoAsiento.ECONOMYC, true),
                service.getSeatAvailabilityInFlightBySeatType(seatDTO, flight)
        );

    }


}