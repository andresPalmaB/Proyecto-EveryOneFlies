package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;

import java.util.List;

public interface SeatService {

    void createSeat(SeatDTO seatDTO, Integer seatNumber, Flight flight);

    Seat getSeatBySeatCode(String seatCode);

    List<Seat> getSeatAvailabilityInFlightBySeatType(SeatDTO seatDTO, Flight flight);

    List<Seat> getSeatAvailabilityInFlight(Flight flight);

    void updateSeatAvailability(Seat seat);

    void deleteSeats(Flight flight);

}
