package com.betek.everyOneFlies.dto;

import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;

import java.time.LocalDate;

public record OutBoundRoute(LocalDate departureDate,
                            AirportDTO aeropuertoOrigenDTO,
                            AirportDTO aeropuertoDestinoDTO) {
}
