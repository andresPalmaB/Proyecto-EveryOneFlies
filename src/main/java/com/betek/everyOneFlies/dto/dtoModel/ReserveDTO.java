package com.betek.everyOneFlies.dto.dtoModel;

import java.time.LocalDate;
import java.util.List;

public record ReserveDTO(LocalDate reservationDate,
                         FlightSeatSoldDTO seatSoldDTO,
                         List<PassengerDTO> passengerDTOList) {
}
