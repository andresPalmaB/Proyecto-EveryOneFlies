package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.modelEnum.SeatCategory;

public record FlightCostDTO(SeatCategory seatCategory, String flightCode) {
}
