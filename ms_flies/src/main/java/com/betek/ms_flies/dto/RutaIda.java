package com.betek.ms_flies.dto;

import com.betek.ms_flies.dto.dtoModel.AeropuertoDTO;

import java.time.LocalDate;

public record RutaIda(LocalDate departureDate,
                      AeropuertoDTO aeropuertoOrigenDTO,
                      AeropuertoDTO aeropuertoDestinoDTO) {
}
