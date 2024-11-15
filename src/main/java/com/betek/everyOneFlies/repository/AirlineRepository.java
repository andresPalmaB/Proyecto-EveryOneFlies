package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

    Optional<Airline> findAirlineByName(String name);

    Optional<Airline> findAirlineByAcronym(String acronym);
}
