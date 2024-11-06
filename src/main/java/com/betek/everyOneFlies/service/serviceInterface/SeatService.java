package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;

import java.util.List;

public interface SeatService {

    void createSeat(SeatDTO seatDTO, int seatNumber, Flight flight);

    List<Seat> getSeatAvailabilityInFlightBySeatType(SeatDTO seatDTO, Flight flight);

    List<Seat> getSeatAvailabilityInFlight(Flight flight);

    void updateSeatAvailability(String seat);

    void deleteSeats(Flight flight);

}
