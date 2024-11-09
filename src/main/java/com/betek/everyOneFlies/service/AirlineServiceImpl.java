package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.repository.AirlineRepository;
import com.betek.everyOneFlies.service.serviceInterface.AirlineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    private final AirlineRepository repository;

    @Override
    public Airline createAirline(AirlineDTO airlineDTO) {

        if (repository.findAirlineByName(airlineDTO.name().toLowerCase()).isPresent()){
            throw new ResourceNotFoundException(
                    "The Airline whit the name " + airlineDTO.name() + " already exists in the database."
            );
        }

        return repository.save(
                new Airline(
                        airlineDTO.name().toLowerCase(),
                        airlineDTO.acronym().toLowerCase()
                )
        );

    }

    @Override
    public Airline getAirlineById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Airline with ID " + id + " not found."
                )
        );
    }

    @Override
    public List<Airline> getAirlines() {
        return repository.findAll();
    }

    @Override
    public Airline getAirlineByName(AirlineDTO airlineDTO) {
        return repository.findAirlineByName(airlineDTO.name().toLowerCase())
                .orElseThrow(
                        ()-> new ResourceNotFoundException(
                        "Airline with the name " + airlineDTO.name() + " not found."
                        )
                );
    }

    @Override
    @Transactional
    public Airline updateAirline(Airline airline) {

        Airline found = this.getAirlineById(airline.getIdAirline());

        found.setName(airline.getName().toLowerCase());
        found.setAcronym(airline.getAcronym().toLowerCase());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteAirlineById(Airline airline) {

        Airline found = this.getAirlineById(airline.getIdAirline());

        if (!found.equals(airline)){
            throw new ResourceNotFoundException(
                    "The airline data does not match the database. Please check and try again."
            );
        }

        Airline saved = new Airline();
        saved.setIdAirline(found.getIdAirline());
        saved.setName(found.getName().toUpperCase());

        repository.delete(airline);

        return new DeleteResponse<>(saved.getClass().getSimpleName(), saved.getName());
    }
}

