package com.betek.everyOneFlies.dto.dtoModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SeatDTO(@NotBlank(message = "is mandatory")
                      String flightCode,

                      @NotNull(message = "is mandatory")
                      @Pattern(regexp = "ECONOMICAL|ECONOMICAL_PREMIUM|BUSINESS|FIRST_CLASS",
                              message = "Invalid status value. Allowed values are: ECONOMICAL, ECONOMICAL_PREMIUM, BUSINESS, FIRST_CLASS")
                      String seatCategory) {
}
