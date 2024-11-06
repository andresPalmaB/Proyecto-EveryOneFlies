package com.betek.everyOneFlies.dto.dtoModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record LocationDTO(String country,
                          String city,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaInicioAlta,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaFinAlta,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaInicioAlta2,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaFinAlta2,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaInicioMedia,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaFinMedia,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaInicioMedia2,

                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate fechaFinMedia2,

                          Double precioBase) {
}
