package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Airport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AirportDTO(@NotBlank(message = "is mandatory")
                         @Size(min = 1, max = 3, message = "must be between 1 and 3 characters")
                         String iataCode,

                         @NotBlank(message = "is mandatory")
                         @Size(min = 1, max = 50, message = "must be between 1 and 50 characters")
                         String name,

                         @NotBlank(message = "is mandatory")
                         @Size(min = 3, max = 30, message = "must be between 3 and 30 characters")
                         String city) {

    public static AirportDTO fromEntity(Airport airport){
        return new AirportDTO(
                airport.getIataCode(),
                airport.getName(),
                airport.getLocation().getCity()
        );
    }
}
