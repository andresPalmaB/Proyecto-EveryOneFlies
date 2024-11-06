package com.betek.everyOneFlies.dto;

import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;

import java.time.LocalDate;

public record ReturnRoute(LocalDate departureDate,
                          LocalDate returnDate,
                          AirportDTO aeropuertoOrigenDTO,
                          AirportDTO aeropuertoDestinoDTO) {
}
