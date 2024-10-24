package com.betek.ms_flies.dto.dtoModel;

import com.betek.ms_flies.model.modelEnum.TipoAsiento;

public record AsientoDTO(Integer asientoDelVuelo,
                         Boolean disponible,
                         TipoAsiento tipoAsiento) {
}
