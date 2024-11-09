package com.betek.everyOneFlies.service;


import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.repository.AirportRepository;
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
            throw new ResourceNotFoundException(
                            "The Airport with IATA code " +
                            airportDTO.iataCode() +
                            " already exists in the database."
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
    public Airport getAirportByIataCode(AirportDTO airportDTO) {
        return repository.findAirportByIataCode(airportDTO.iataCode().toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Airport with IATA code " + airportDTO.iataCode() + " not found."));
    }

    @Override
    public Airport getAirportByName(AirportDTO airportDTO) {
        return repository.findAirportByName(airportDTO.name().toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Airport with name " + airportDTO.name() + " not found."));
    }

    @Override
    public List<Airport> getAirportByCity(AirportDTO airportDTO) {

        Location location = locationService.getLocationByCity(airportDTO.city().toLowerCase());

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
