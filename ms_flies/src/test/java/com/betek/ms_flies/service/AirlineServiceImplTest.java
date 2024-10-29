package com.betek.ms_flies.service;

import com.betek.ms_flies.MsFliesApplication;
import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirlineDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Airline;
import com.betek.ms_flies.repository.AirlineRepository;
import com.betek.ms_flies.service.serviceInterface.AirlineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MsFliesApplication.class)
class AirlineServiceImplTest {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private AirlineRepository repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void createAirline_shouldThrowExceptionIfExistsAndIfNotCreatesPersistence(){

        AirlineDTO airlineDTO = new AirlineDTO(
                "Avianca",
                "AV"
        );

        Airline resultado = airlineService.createAirline(airlineDTO);
        System.out.println(resultado.getIdAirline());
        Airline resultado2 = airlineService.getAirlineById(1);

        Airline airlineToCompare = new Airline();
        airlineToCompare.setName("Avianca");
        airlineToCompare.setAcronym("AV");

        assertThrows(ResourceNotFoundException.class, () -> {
            airlineService.createAirline(airlineDTO);
        });

        assertEquals(airlineToCompare.getName().toLowerCase(), resultado.getName());
        assertEquals(airlineToCompare.getAcronym().toLowerCase(), resultado.getAcronym());

        assertEquals(resultado2.getIdAirline(), resultado.getIdAirline());

    }

    @Test
    void getAirlineOrAirlines_shouldGetAirlinebyIdByNameAndListToAirline(){

        AirlineDTO airlineDTO = new AirlineDTO(
                "Avianca",
                "AV"
        );

        AirlineDTO airlineDTO2 = new AirlineDTO(
                "LATAM",
                "LAM"
        );

        Airline expected = airlineService.createAirline(airlineDTO);
        Airline expected2 = airlineService.createAirline(airlineDTO2);

        List<Airline> airlineList = new ArrayList<>();
        airlineList.add(expected);
        airlineList.add(expected2);

        assertEquals(expected, airlineService.getAirlineById(1));

        assertEquals(expected2, airlineService.getAirlineByName(airlineDTO2));

        assertIterableEquals(airlineList, airlineService.getAirlines());
    }

    @Test
    void updateAirline_shouldThrowExeptionIfNotFoundAndIfFoundReturnUpdate(){

        AirlineDTO airlineDTO = new AirlineDTO(
                "Avianca",
                "AV"
        );

        AirlineDTO airlineDTO2 = new AirlineDTO(
                "LATAM",
                "LAM"
        );

        Airline ifNotFound = airlineService.createAirline(airlineDTO);
        ifNotFound.setIdAirline(404);

        Airline update = new Airline(airlineDTO2);
        update.setIdAirline(1);

        Airline expected = airlineService.updateAirline(update);

        assertEquals(expected, repository.findAll().getFirst());

        assertThrows(ResourceNotFoundException.class, () -> {
            airlineService.updateAirline(ifNotFound);
        });

    }

    @Test
    void deleteAirline_shouldThrowExeptionIfNotFoundAndIfFoundReturnDelete(){

        Airline airline = new Airline();

        assertThrows(ResourceNotFoundException.class, () -> {
            airlineService.deleteAirlineById(airline);
        });

        AirlineDTO airlineDTO = new AirlineDTO(
                "Avianca",
                "AV"
        );

        Airline toDelete = airlineService.createAirline(airlineDTO);

        DeleteResponse<Airline> expected = airlineService.deleteAirlineById(toDelete);

        assertEquals(expected, new DeleteResponse<>(toDelete.getClass().getSimpleName(), toDelete.getName()));

    }


}