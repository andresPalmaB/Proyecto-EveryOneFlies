package com.betek.ms_flies.dto.dtoModel;

import com.betek.ms_flies.model.modelEnum.TipoAsiento;

public record SeatDTO(String flightCode,
                      Boolean disponible,
                      TipoAsiento tipoAsiento) {
}
