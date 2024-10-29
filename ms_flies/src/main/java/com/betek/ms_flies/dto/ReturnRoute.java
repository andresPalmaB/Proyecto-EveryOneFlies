package com.betek.ms_flies.dto;

import com.betek.ms_flies.dto.dtoModel.AirportDTO;

import java.time.LocalDate;

public record ReturnRoute(LocalDate departureDate,
                          LocalDate returnDate,
                          AirportDTO aeropuertoOrigenDTO,
                          AirportDTO aeropuertoDestinoDTO) {
}
