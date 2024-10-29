package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Integer> {

    Optional<Airport> findAirportByIataCode(String codigo);

    Optional<Airport> findAirportByName(String nombre);

    List<Airport> findAirportByLocation(Location location);

    List<Airport> findAirportByLocationInOrderByIdAirportAsc(List<Location> location);

}
