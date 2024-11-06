package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.modelEnum.TipoAsiento;

public record SeatDTO(String flightCode,
                      Boolean disponible,
                      TipoAsiento tipoAsiento) {
}
