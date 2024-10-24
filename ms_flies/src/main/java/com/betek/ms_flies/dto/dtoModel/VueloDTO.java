package com.betek.ms_flies.dto.dtoModel;

import java.time.LocalDate;
import java.time.LocalTime;

public record VueloDTO(Integer idAerolinea,
                       Integer idAeropuertoOrigen,
                       Integer idAeropuertoDestino,
                       LocalDate fechaSalida,
                       LocalTime horaSalida,
                       LocalDate fechaLlegada,
                       LocalTime horaLlegada,
                       Integer contadorEconomicos,
                       Integer contadorEconomicosPremiun,
                       Integer contadorBusiness,
                       Integer contadorPrimeraClase) {
}
