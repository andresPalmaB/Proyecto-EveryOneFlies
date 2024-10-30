package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.dtoModel.SeatDTO;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;

import java.util.List;

public interface SeatService {

    void createSeat(SeatDTO seatDTO, int seatNumber, Flight flight);

    List<Seat> getSeatAvailabilityInFlightBySeatType(SeatDTO seatDTO, Flight flight);

    List<Seat> getSeatAvailabilityInFlight(Flight flight);

    void updateSeatAvailability(String seat);

    void deleteSeats(Flight flight);

}
