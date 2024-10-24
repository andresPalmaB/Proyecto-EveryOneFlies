package com.betek.ms_flies.dto;

import com.betek.ms_flies.dto.dtoModel.AeropuertoDTO;

import java.time.LocalDate;

public record RutaIdaVuelta(LocalDate departureDate,
                            LocalDate returnDate,
                            AeropuertoDTO aeropuertoOrigenDTO,
                            AeropuertoDTO aeropuertoDestinoDTO) {
}
