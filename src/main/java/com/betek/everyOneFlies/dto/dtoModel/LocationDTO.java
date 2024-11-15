package com.betek.everyOneFlies.dto.dtoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record LocationDTO(@NotBlank(message = "is mandatory")
                          @Size(min = 4, max = 30, message = "must be between 4 and 30 characters")
                          String country,

                          @NotBlank(message = "is mandatory")
                          @Size(min = 3, max = 30, message = "must be between 3 and 30 characters")
                          String city,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate startDateH,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate endDateH,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate startDateH2,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate endDateH2,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate startDateM,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate endDateM,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate startDateM2,

                          @NotNull(message = "is mandatory")
                          @FutureOrPresent(message = "must be in the future")
                          @JsonFormat(pattern = "yyyy-MM-dd")
                          LocalDate endDateM2,

                          @NotNull(message = "is mandatory")
                          Double basePrice) {
}
