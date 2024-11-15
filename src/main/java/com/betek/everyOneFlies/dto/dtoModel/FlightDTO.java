package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Flight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightDTO(@NotBlank(message = "is mandatory")
                        @Size(min = 1, max = 15, message = "must be between 1 and 15 characters")
                        String airlineName,

                        @NotBlank(message = "is mandatory")
                        @Size(min = 1, max = 3, message = "must be between 1 and 3 characters")
                        String iataCodeOriginAirport,

                        @NotBlank(message = "is mandatory")
                        @Size(min = 1, max = 3, message = "must be between 1 and 3 characters")
                        String iataCodeDestinationAirport,

                        @NotNull(message = "is mandatory")
                        @FutureOrPresent(message = "must be in the future")
                        @JsonFormat(pattern = "yyyy-MM-dd")
                        LocalDate departureDate,

                        @NotNull(message = "is mandatory")
                        @JsonFormat(pattern = "HH:mm")
                        @Schema(type = "string", example = "15:30", description = "Time in HH:mm format")
                        LocalTime departureTime,

                        @NotNull(message = "is mandatory")
                        @FutureOrPresent(message = "must be in the future")
                        @JsonFormat(pattern = "yyyy-MM-dd")
                        LocalDate arrivalDate,

                        @NotNull(message = "is mandatory")
                        @JsonFormat(pattern = "HH:mm")
                        @Schema(type = "string", example = "15:30", description = "Time in HH:mm format")
                        LocalTime arrivalTime,

                        @NotNull(message = "is mandatory")
                        Integer economyCounter,

                        @NotNull(message = "is mandatory")
                        Integer premiumEconomyCounter,

                        @NotNull(message = "is mandatory")
                        Integer businessCounter,

                        @NotNull(message = "is mandatory")
                        Integer firstClassCounter) {

}
