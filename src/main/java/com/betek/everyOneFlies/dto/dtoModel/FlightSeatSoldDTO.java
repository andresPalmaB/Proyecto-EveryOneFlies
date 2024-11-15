package com.betek.everyOneFlies.dto.dtoModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FlightSeatSoldDTO(@NotBlank(message = "is mandatory")
                                String flightCode,

                                @NotNull(message = "is mandatory")
                                Integer seatsSoldEconomy,

                                @NotNull(message = "is mandatory")
                                Integer seatsSoldPremiumEconomy,

                                @NotNull(message = "is mandatory")
                                Integer seatsSoldBusiness,

                                @NotNull(message = "is mandatory")
                                Integer seatsSoldFirstClass,

                                @NotNull(message = "is mandatory")
                                @Size(min = 1, message = "must have at least one element")
                                List<String> seatCodes) {
}
