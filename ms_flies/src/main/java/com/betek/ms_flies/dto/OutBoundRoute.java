package com.betek.ms_flies.dto;

import com.betek.ms_flies.dto.dtoModel.AirportDTO;

import java.time.LocalDate;

public record OutBoundRoute(LocalDate departureDate,
                            AirportDTO aeropuertoOrigenDTO,
                            AirportDTO aeropuertoDestinoDTO) {
}
