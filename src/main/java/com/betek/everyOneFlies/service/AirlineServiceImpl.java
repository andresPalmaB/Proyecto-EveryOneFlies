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
                    "La Aerolinea con name " + airlineDTO.name() + " ya existe en la base de datos."
            );
        }

        return repository.save(
                new Airline(
                        airlineDTO
                )
        );

    }

    @Override
    public Airline getAirlineById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Aerolinea con ID " + id + " no encontrada."
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
                        "Aerolinea por name " + airlineDTO.name() + " no encontrada."
                        )
                );
    }

    @Override
    @Transactional
    public Airline updateAirline(Airline airline) {

        Airline found = repository.findById(airline.getIdAirline())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Aerolinea con ID " + airline.getIdAirline() + " no encontrada."
                        )
                );

        found.setName(airline.getName().toLowerCase());
        found.setAcronym(airline.getAcronym().toLowerCase());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteAirlineById(Airline airline) {

        Airline found = repository.findById(airline.getIdAirline())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                        airline.getClass().getSimpleName() + " con ID " + airline.getIdAirline() + " no encontrado")
                );

        Airline guardado = new Airline();
        guardado.setIdAirline(found.getIdAirline());
        guardado.setName(found.getName().toUpperCase());

        repository.delete(airline);

        return new DeleteResponse<>(guardado.getClass().getSimpleName(), guardado.getName());
    }
}

