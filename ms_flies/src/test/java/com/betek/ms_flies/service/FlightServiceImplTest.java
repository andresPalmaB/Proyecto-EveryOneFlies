package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.OutBoundRoute;
import com.betek.ms_flies.dto.ReturnRoute;
import com.betek.ms_flies.dto.dtoModel.AirlineDTO;
import com.betek.ms_flies.dto.dtoModel.AirportDTO;
import com.betek.ms_flies.dto.dtoModel.FlightDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.repository.FlightRepository;
import com.betek.ms_flies.service.serviceInterface.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlightServiceImplTest {

    @Autowired
    private FlightService service;

    @Autowired
    private FlightRepository repository;

    private FlightDTO flightDTO;

    private AirlineDTO airlineDTO1;

    private AirportDTO airportBAQ;

    private AirportDTO airportMDE;

    private AirportDTO airportBOG;

    @BeforeEach
    public void init(){

        airlineDTO1 = new AirlineDTO(
                "everyoneflies",
                "eof"
        );

        airportBAQ = new AirportDTO(
                "BAQ",
                "Aeropuerto Internacional Ernesto Cortissoz",
                "Barranquilla"
        );

        airportMDE = new AirportDTO(
                "mde",
                "aeropuerto internacional josé maría córdova",
                "Medellín"
        );

        airportBOG = new AirportDTO(
                "BOG",
                "Aeropuerto Internacional El Dorado",
                "Bogotá"
        );

        flightDTO = new FlightDTO(
                airlineDTO1,
                airportBAQ,
                airportBOG,
                LocalDate.parse("2024-12-15"),
                LocalTime.parse("10:00:00"),
                LocalDate.parse("2024-12-15"),
                LocalTime.parse("12:00:00"),
                6,
                3,
                2,
                1
        );
    }

    @Test
    void createFlight_shouldThrowExceptionIfExistsAndIfNotCreatePersistence(){

        Flight expected = service.getFlightByFlightCode("eof1");

        // Intenta crear el mismo Airport persistido de nuevo y espera una excepcion
        assertThrows(ResourceNotFoundException.class, () -> service.createFlight(flightDTO));

        assertEquals(expected, repository.findFlightByFlightCode("eof1").orElse(null));

    }

    @Test
    void getFlightOrFlights_shouldGetFlightByFlightCodeByRouteByDateByRutaByRangoFechas(){

        OutBoundRoute route = new OutBoundRoute(LocalDate.parse("2024-12-15"), airportBAQ, airportBOG);
        ReturnRoute route2 =
                new ReturnRoute(
                        LocalDate.parse("2024-12-15"),
                        LocalDate.parse("2024-12-18"),
                        airportBAQ,
                        airportBOG
                );

        Flight expected1 = repository.findFlightByFlightCode("eof1").orElse(null);
        Flight expected2 = repository.findFlightByFlightCode("eof2").orElse(null);
        Flight expected5 = repository.findFlightByFlightCode("eof5").orElse(null);

        //Verifica que se obtine vuelo por flightCode
        assertEquals(expected1, service.getFlightByFlightCode("eof1"));

        //Verifica que se obtiene vuelo por OutBoundRoute
        List<Flight> flightList = new ArrayList<>();
        Collections.addAll(flightList, expected1, expected2);

        assertIterableEquals(flightList, service.getRouteByDate(route));

        //Verifica que se obtine vuelo por ReturnRoute
        flightList.clear();
        Collections.addAll(flightList, expected1, expected2, expected5);

        assertIterableEquals(flightList, service.getRutaByRangoFechas(route2));

    }

    @Test
    void updateDateTimeAndAmountSeatsSold_shouldThrowExceptionIfNotFoundAndIfFoundReturnUpdate(){

        Flight update = repository.findFlightByFlightCode("eof1").orElse(null);
        assert update != null;
        update.setDepartureDate(LocalDate.parse("2024-12-16"));
        update.setDepartureTime(LocalTime.parse("13:00:00"));
        update.setArrivalDate(LocalDate.parse("2024-12-16"));
        update.setArrivalTime(LocalTime.parse("15:00:00"));

        service.updateFlightDateTime(update);

        assertEquals(update, repository.findFlightByFlightCode("eof1").orElse(null));



    }

}