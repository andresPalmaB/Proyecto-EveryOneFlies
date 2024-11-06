package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Airport;

public record AirportDTO(String iataCode,
                         String name,
                         String city) {
    public static AirportDTO fromEntity(Airport airport){
        return new AirportDTO(
                airport.getIataCode(),
                airport.getName(),
                airport.getLocation().getCity()
        );
    }
}
