package com.betek.everyOneFlies.service;


import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.betek.everyOneFlies.exception.ExistingResourceException;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.repository.AirportRepository;
import com.betek.everyOneFlies.repository.LocationRepository;
import com.betek.everyOneFlies.service.serviceInterface.AirportService;
import com.betek.everyOneFlies.service.serviceInterface.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Repository
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository repository;

    @Autowired
    private LocationService locationService;

    @Override
    public Airport createAirport(AirportDTO airportDTO) {

        Location location = locationService.getLocationByCity(airportDTO.city());

        if (repository.findAirportByIataCode(airportDTO.iataCode().toLowerCase()).isPresent()){
            throw new ExistingResourceException(
                    "The Airport with IATA code " + airportDTO.iataCode() + " already exists in the database."
            );
        }

        if (repository.findAirportByName(airportDTO.name().toLowerCase()).isPresent()){
            throw new ExistingResourceException(
                    "The Airport with name " + airportDTO.name() + " already exists in the database."
            );
        }

        return repository.save(
                new Airport(
                        airportDTO.iataCode().toLowerCase(),
                        airportDTO.name().toLowerCase(),
                        location
                )
        );
    }

    @Override
    public Airport getAirportById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Airport with ID " + id + " not found."));
    }

    @Override
    public Airport getAirportByIataCode(String iataCode) {
        return repository.findAirportByIataCode(iataCode.toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Airport with IATA code " + iataCode + " not found."));
    }

    @Override
    public Airport getAirportByName(String name) {
        return repository.findAirportByName(name.toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Airport with name " + name + " not found."));
    }

    @Override
    public List<Airport> getAirportByCity(String city) {

        Location location = locationService.getLocationByCity(city.toLowerCase());

        return repository.findAirportByLocation(location);
    }

    @Override
    public List<Airport> getAirportsByCountry(String country) {

        return repository.findAirportByLocationInOrderByIdAirportAsc(
                locationService.getLocationByCountry(country.toLowerCase())
        );

    }

    @Override
    public List<Airport> getAirports() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Airport updateAirport(Airport airport) {

        Airport found = this.getAirportById(airport.getIdAirport());

        found.setName(airport.getName());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteAirport(Airport airport) {

        Airport found = this.getAirportById(airport.getIdAirport());

        if (!found.equals(airport)){
            throw new ResourceNotFoundException(
                    "The airport data does not match the database. Please check and try again."
            );
        }

        Airport saved = new Airport();
        saved.setName(found.getName());

        repository.delete(airport);

        return new DeleteResponse<>(saved.getClass().getSimpleName(), saved.getName());
    }
}
