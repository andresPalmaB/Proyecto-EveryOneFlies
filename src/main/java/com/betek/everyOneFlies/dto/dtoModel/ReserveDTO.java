package com.betek.everyOneFlies.dto.dtoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record ReserveDTO(@NotNull(message = "is mandatory")
                         @FutureOrPresent(message = "must be in the future")
                         @JsonFormat(pattern = "yyyy-MM-dd")
                         LocalDate reservationDate,

                         FlightSeatSoldDTO seatSoldDTO,

                         @NotNull(message = "is mandatory")
                         @Size(min = 1, message = "must have at least one element")
                         @Valid List<PassengerDTO> passengerDTOList) {
}
