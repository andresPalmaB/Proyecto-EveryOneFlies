package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.modelEnum.SeatCategory;

public record SeatDTO(String flightCode,
                      Boolean disponible,
                      SeatCategory tipoAsiento) {
}
