package com.betek.everyOneFlies.dto.dtoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record GenerateTicketDTO(@NotNull(message = "is mandatory")
                                @Schema(description = "ID of the ticket", example = "12345")
                                Long idTicket,

                                @NotNull(message = "is mandatory")
                                @FutureOrPresent(message = "must be in the future")
                                @Schema(type = "string", format = "date-time", description = "Current date and time in format yyyy-MM-dd HH:mm", example = "2024-11-15 10:30")
                                @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                                LocalDateTime now) {
}
