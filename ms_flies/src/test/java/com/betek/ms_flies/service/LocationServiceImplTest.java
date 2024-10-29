package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.repository.LocationRepository;
import com.betek.ms_flies.service.serviceInterface.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    private LocationService service;

    @Autowired
    private LocationRepository repository;

    private LocationDTO locationDTO;

    private LocationDTO locationDTO2;

    private LocationDTO locationDTO3;

    @BeforeEach
    public void init() {

        repository.deleteAll();

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
                "Medellin",
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
                "Venezuela",
                "Maracaibo",
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
    void createLocation_shouldThrowExceptionIfExistsAndIfNotCreatesPersistence(){

        Location expected = service.createLocation(locationDTO);

        // Intenta crear la misma location de nuevo y espera una excepcion
        assertThrows(ResourceNotFoundException.class, () -> {
            service.createLocation(locationDTO);
        });

        // Verifica que la location creada exista en el repository
        Location actual = repository.findById(expected.getIdLocation()).orElse(null);
        assertEquals(expected, actual);

    }

    @Test
    void getLocationOrLocations_shouldGetLocationbyIdByCityByCountryAndListToLocation(){

        Location expected = service.createLocation(locationDTO);
        Location expected2 = service.createLocation(locationDTO2);
        Location expected3 = service.createLocation(locationDTO3);

        List<Location> locationList = new ArrayList<>();
        locationList.add(expected);
        locationList.add(expected2);

        //Verifica que se obtiene Location por city
        assertEquals(expected, service.getLocationById(1));

        //Verifica que se obtiene Location por ciudad
        assertEquals(expected, service.getLocationByCity("Barranquilla"));

        //Verifica que se obtiene Location por pais
        assertEquals(locationList, service.getLocationByCountry("Colombia"));

        locationList.add(expected3);

        //Verifica que se obtienen todos los registro que existen en el repository
        assertIterableEquals(locationList, service.getLocations());

    }

    @Test
    void deleteLocation_shouldThrowExeptionIfNotFoundAndIfFoundReturnDelete(){

        Location location = new Location();

        // Intenta eliminar una Location que no existe y manda una excepcion
        assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteLocationById(location);
        });

        Location toDelete = service.createLocation(locationDTO);

        DeleteResponse<Location> expected = service.deleteLocationById(toDelete);

        // Verifica que se haya eliminado el registro
        assertEquals(expected, new DeleteResponse<>(toDelete.getClass().getSimpleName(), toDelete.getCity()));

    }

}