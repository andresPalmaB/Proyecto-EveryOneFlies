package com.betek.everyOneFlies.dto;

import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record OutBoundRoute(@NotNull(message = "is mandatory")
                            @FutureOrPresent(message = "must be in the future")
                            @Pattern(
                                    regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
                                    message = "must be in the format yyyy-MM-dd and must be a valid date"
                            )
                            @JsonFormat(pattern = "yyyy-MM-dd")
                            String departureDate,

                            @NotBlank(message = "is mandatory")
                            String iataCodeOriginAirport,

                            @NotBlank(message = "is mandatory")
                            String iataCodeDestinationAirport) {
}
