package com.betek.everyOneFlies.exception;

import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;

public final class ValidateArguments {
    public static SeatCategory validateSeatCategory(String seatCategory) {
        try {
            return SeatCategory.valueOf(seatCategory.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid seat category: " + seatCategory);
        }
    }

    public static ReservationStatus validateReservationStatus(String reservationStatus) {
        try {
            return ReservationStatus.valueOf(reservationStatus.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid reservation status: " + reservationStatus);
        }
    }

    public static TicketStatus validateTicketStatus(String ticketStatus) {
        try {
            return TicketStatus.valueOf(ticketStatus.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid ticket status: " + ticketStatus);
        }
    }
}
