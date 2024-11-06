package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Airline;

public record AirlineDTO(String name,
                         String acronym) {

    public static AirlineDTO fromEntity(Airline airline){
        return new AirlineDTO(
                airline.getName(),
                airline.getAcronym()
        );
    }
}
