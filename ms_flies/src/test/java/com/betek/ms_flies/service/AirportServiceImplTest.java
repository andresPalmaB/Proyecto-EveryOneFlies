package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirportDTO;
import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.repository.AirportRepository;
import com.betek.ms_flies.service.serviceInterface.AirportService;
import com.betek.ms_flies.service.serviceInterface.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AirportServiceImplTest {

    @Autowired
    private AirportService service;

    @Autowired
    private AirportRepository repository;

    private AirportDTO airportDTO;

    private AirportDTO airportDTO2;

    private AirportDTO airportDTO3;

    private AirportDTO airportDTO4;

    @Autowired
    private LocationService locationService;

    private LocationDTO locationDTO;

    private LocationDTO locationDTO2;

    private LocationDTO locationDTO3;

    @BeforeEach
    public void init(){

        repository.deleteAll();

        airportDTO = new AirportDTO(
                "BAQ",
                "Aeropuerto Internacional Ernesto Cortissoz",
                "Barranquilla"
        );

        airportDTO2 = new AirportDTO(
                "MDE",
                "Aeropuerto Internacional José María Córdova",
                "Medellín"
        );

        airportDTO3 = new AirportDTO(
                "BOG",
                "Aeropuerto Internacional El Dorado",
                "Bogotá"
        );

        airportDTO4 = new AirportDTO(
                "BRR",
                "Aeropuerto Internacional de Barranquilla",
                "Barranquilla"
        );

        locationDTO = new LocationDTO(
                "Colombia",
                "Barranquilla",
                LocalDate.of(2024,12,1),
                LocalDate.of(2024, 1,31),
                LocalDate.of(2024, 2,1),
                LocalDate.of(2024, 3,31),
                LocalDate.of(2024, 4,1),
                LocalDate.of(2024, 4,30),
                LocalDate.of(2024, 7,1),
                LocalDate.of(2024, 8,31),
                100000d
        );

        locationDTO2 = new LocationDTO(
                "Colombia",
                "Medellín",
                LocalDate.of(2024,12,1),
                LocalDate.of(2024, 1,31),
                LocalDate.of(2024, 2,1),
                LocalDate.of(2024, 3,31),
                LocalDate.of(2024, 4,1),
                LocalDate.of(2024, 4,30),
                LocalDate.of(2024, 7,1),
                LocalDate.of(2024, 8,31),
                100000d
        );

        locationDTO3 = new LocationDTO(
                "Colombia",
                "Bogotá",
                LocalDate.of(2024,12,1),
                LocalDate.of(2024, 1,31),
                LocalDate.of(2024, 2,1),
                LocalDate.of(2024, 3,31),
                LocalDate.of(2024, 4,1),
                LocalDate.of(2024, 4,30),
                LocalDate.of(2024, 7,1),
                LocalDate.of(2024, 8,31),
                100000d
        );
    }

    @Test
    void createAirport_shouldThrowExceptionIfExistsAndIfNotCreatePersistence(){

        locationService.createLocation(locationDTO);

        Airport expected = service.createAirport(airportDTO);

        // Intenta crear la misma location de nuevo y espera una excepcion
        assertThrows(ResourceNotFoundException.class, () -> service.createAirport(airportDTO));

        // Verifica que la location creada exista en el repository
        Airport actual = repository.findById(expected.getIdAirport()).orElse(null);
        assertEquals(expected, actual);

    }

    @Test
    void getAirportOrAirports_shouldGetAirportbyIdByIataCodeByNameByCityByCountryAndListToAirports(){

        locationService.createLocation(locationDTO);
        locationService.createLocation(locationDTO2);
        locationService.createLocation(locationDTO3);

        Airport expected = service.createAirport(airportDTO);
        Airport expected2 = service.createAirport(airportDTO2);
        Airport expected3 = service.createAirport(airportDTO3);
        Airport expected4 = service.createAirport(airportDTO4);


        List<Airport> airportList = new ArrayList<>();
        airportList.add(expected);
        airportList.add(expected2);
        airportList.add(expected3);
        airportList.add(expected4);

        //Verifica que se obtiene Airport por idAirport.
        assertEquals(expected, service.getAirportById(1));

        //Verifica que se obtiene Airport por iataCode.
        assertEquals(expected2, service.getAirportByIataCode(airportDTO2));

        //Verifica que se obtiene Airport por name.
        assertEquals(expected3, service.getAirportByName(airportDTO3));

        //Verifica que se obtiene Airport por ciudad
        List<Airport> airportList1 = new ArrayList<>();
        airportList1.add(expected);
        airportList1.add(expected4);
        assertEquals(airportList1, service.getAirportByCity(airportDTO));

        //Verifica que se obtiene Airport por pais
        assertIterableEquals(airportList, service.getAirportsByCountry("Colombia"));

        //Verifica que se obtienen todos los registro que existen en el repository
        assertIterableEquals(airportList, service.getAirports());
    }

    @Test
    void updateAirport_shouldThrowExeptionIfNotFoundAndIfFoundReturnUpdate(){

        locationService.createLocation(locationDTO);
        Location location = locationService.createLocation(locationDTO2);
        locationService.createLocation(locationDTO3);

        Airport update = service.createAirport(airportDTO);
        update.setName("Aeropuerto Internacional de Barranquilla");

        Airport expected = service.updateAirportByIataCode(update);

        Optional<Airport> airportOptional = repository.findById(update.getIdAirport());

        if (airportOptional.isEmpty()){
            throw new ResourceNotFoundException("hola");
        }

        assertEquals(expected, airportOptional.get());

        assertThrows(ResourceNotFoundException.class, () ->
                service.updateAirportByIataCode(new Airport(airportDTO2.iataCode(), airportDTO2.name(), location)));

    }

    @Test
    void deleteLocation_shouldThrowExeptionIfNotFoundAndIfFoundReturnDelete(){

        locationService.createLocation(locationDTO);

        Airport toDelete = new Airport();

        Airport finalToDelete = toDelete;
        assertThrows(ResourceNotFoundException.class, () -> service.deleteAirportByIataCode(finalToDelete));

        toDelete = service.createAirport(airportDTO);

        DeleteResponse<Airport> expected = service.deleteAirportByIataCode(toDelete);

        assertEquals(expected, new DeleteResponse<>(toDelete.getClass().getSimpleName(), toDelete.getName()));

    }
}