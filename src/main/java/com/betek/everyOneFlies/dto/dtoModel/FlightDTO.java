package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Flight;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightDTO(AirlineDTO airlineDTO,
                        AirportDTO originAirportDTO,
                        AirportDTO destinationAirportDTO,
                        LocalDate departureDate,
                        LocalTime departureTime,
                        LocalDate arrivalDate,
                        LocalTime arrivalTime,
                        Integer economyCounter,
                        Integer premiumEconomyCounter,
                        Integer businessCounter,
                        Integer firstClassCounter) {

    public static FlightDTO fromEntity(Flight flight){
        return new FlightDTO(
                AirlineDTO.fromEntity(flight.getAirline()),
                AirportDTO.fromEntity(flight.getOrigin()),
                AirportDTO.fromEntity(flight.getDestination()),
                flight.getDepartureDate(),
                flight.getDepartureTime(),
                flight.getArrivalDate(),
                flight.getArrivalTime(),
                flight.getEconomyCounter(),
                flight.getPremiumEconomyCounter(),
                flight.getBusinessCounter(),
                flight.getFirstClassCounter()
        );
    }
}
