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

        //Se verifica que la location exista en el LocationRepository
        Location location = locationService.getLocationByCity(airportDTO.city());

        if (repository.findAirportByIataCode(airportDTO.iataCode().toLowerCase()).isPresent()){
            throw new ResourceNotFoundException(
                    "El Aeropuerto con flightCode IATA " + airportDTO.iataCode() + " ya existe en la base de datos."
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
                        "Aeropuerto por ID " + id + " no encontrada."));
    }

    @Override
    public Airport getAirportByIataCode(AirportDTO airportDTO) {
        return repository.findAirportByIataCode(airportDTO.iataCode().toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aeropuerto por CodigoIATA " + airportDTO.iataCode() + " no encontrada."));
    }

    @Override
    public Airport getAirportByName(AirportDTO airportDTO) {
        return repository.findAirportByName(airportDTO.name().toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aeropuerto por Nombre " + airportDTO.name() + " no encontrada."));
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
    public Airport updateAirportByIataCode(Airport airport) {

        Airport found = repository.findById(airport.getIdAirport())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aeropuerto con flightCode " + airport.getIdAirport() + " no encontrada."));

        found.setName(airport.getName());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteAirportByIataCode(Airport airport) {

        Airport object = repository.findById(airport.getIdAirport())
                .orElseThrow(() -> new ResourceNotFoundException(
                        airport.getClass().getSimpleName() + " con ID " +
                                airport.getIdAirport() + " no encontrado"));

        Airport save = new Airport();
        save.setName(object.getName());

        repository.delete(airport);

        return new DeleteResponse<>(save.getClass().getSimpleName(), save.getName());
    }
}
