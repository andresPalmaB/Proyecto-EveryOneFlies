package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Airline;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AirlineDTO(@NotBlank(message = "is mandatory")
                         @Size(min = 1, max = 15, message = "must be between 1 and 15 characters")
                         String name,

                         @NotBlank(message = "is mandatory")
                         @Size(min = 1, max = 3, message = "must be between 1 and 3 characters.")
                         String acronym) {

    public static AirlineDTO fromEntity(Airline airline){
        return new AirlineDTO(
                airline.getName(),
                airline.getAcronym()
        );
    }

}
