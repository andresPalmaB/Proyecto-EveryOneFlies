package com.betek.everyOneFlies.dto.dtoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateReserveStatusDTO(@NotBlank(message = "is mandatory")
                                     String reserveCode,

                                     @NotBlank(message = "is mandatory")
                                     String newStatus,

                                     @NotNull(message = "is mandatory")
                                     @FutureOrPresent(message = "must be in the future")
                                     @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
                                     LocalDateTime now) {
}
