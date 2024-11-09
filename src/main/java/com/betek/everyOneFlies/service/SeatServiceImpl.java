package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.repository.SeatRepository;
import com.betek.everyOneFlies.service.serviceInterface.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository repository;

    @Override
    public void createSeat(SeatDTO seatDTO, Integer seatNumber, Flight flight) {

        repository.save(
                new Seat(
                        flight,
                        seatDTO.available(),
                        seatDTO.seatCategory(),
                        seatNumber
                ));
    }

    @Override
    public Seat getSeatBySeatCode(String seatCode){
        return repository.findSeatBySeatCode(seatCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Seat with Code " + seatCode + " not found."));

    }

    @Override
    public List<Seat> getSeatAvailabilityInFlightBySeatType(SeatDTO seatDTO, Flight flight) {
        return repository.findSeatByFlightAndSeatCategoryAndAvailable(
                flight,
                seatDTO.seatCategory(),
                true
        );
    }

    @Override
    public List<Seat> getSeatAvailabilityInFlight(Flight flight) {

        return repository.findSeatByFlightAndAvailable(
                flight,
                true
        );
    }

    @Override
    public void updateSeatAvailability(Seat seat) {

        Seat found = this.getSeatBySeatCode(seat.getSeatCode());

        found.setAvailable(false);

        repository.save(found);

    }

    @Override
    public void deleteSeats(Flight flight) {
        repository.deleteAllByFlight(flight);
    }
}
