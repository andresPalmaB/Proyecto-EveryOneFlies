package com.betek.ms_flies.dto.dtoModel;

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
}
