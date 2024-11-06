package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findFlightByFlightCode(java.lang.String flightCode);

    List<Flight> findFlightByDepartureDateAndOriginAndDestinationOrderByFlightCodeAsc(LocalDate departureDate,
                                                                                      Airport origin,
                                                                                      Airport destination);

    List<Flight> findFlightByDepartureDateBetweenAndOriginAndDestinationOrderByFlightCodeAsc(LocalDate startOfDay,
                                                                                             LocalDate endOfDay,
                                                                                             Airport origin,
                                                                                             Airport destination);

    boolean existsByAirlineAndOriginAndDestinationAndDepartureDateAndDepartureTime(Airline airline,
                                                                                   Airport origin,
                                                                                   Airport destination,
                                                                                   LocalDate departureDate,
                                                                                   LocalTime departureTime);
}
