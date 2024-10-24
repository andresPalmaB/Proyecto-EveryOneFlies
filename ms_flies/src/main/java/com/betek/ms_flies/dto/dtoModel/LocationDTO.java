package com.betek.ms_flies.dto.dtoModel;

import java.time.LocalDate;

public record LocationDTO(String country,
                          String city,
                          LocalDate fechaInicioAlta,
                          LocalDate fechaFinAlta,
                          LocalDate fechaInicioMedia,
                          LocalDate fechaFinMedia,
                          LocalDate fechaInicioMedia2,
                          LocalDate fechaFinMedia2,
                          Double precioBase) {
}
