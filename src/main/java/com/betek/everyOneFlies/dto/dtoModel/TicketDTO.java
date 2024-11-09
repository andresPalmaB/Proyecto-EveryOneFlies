package com.betek.everyOneFlies.dto.dtoModel;

import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.model.Reserve;

import java.time.LocalDate;

public record TicketDTO(Passenger passenger,
                        LocalDate issueDate,
                        TicketStatus ticketStatus) {
}
