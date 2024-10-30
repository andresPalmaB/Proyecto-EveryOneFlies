package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.dtoModel.SeatDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;
import com.betek.ms_flies.repository.SeatRepository;
import com.betek.ms_flies.service.serviceInterface.SeatService;
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
    public void createSeat(SeatDTO seatDTO, int seatNumber, Flight flight) {

        repository.save(
                new Seat(
                        flight,
                        seatDTO.disponible(),
                        seatDTO.tipoAsiento(),
                        seatNumber
                ));
    }

    @Override
    public List<Seat> getSeatAvailabilityInFlightBySeatType(SeatDTO seatDTO, Flight flight) {
        return repository.findSeatByFlightAndTipoAsientoAndAvailable(
                flight,
                seatDTO.tipoAsiento(),
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
    public void updateSeatAvailability(String seat) {

        Seat found = repository.findSeatBySeatCode(seat)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Asiento con codigo " + seat + " no encontrado"));

        found.setAvailable(false);

        repository.save(found);

    }

    @Override
    @Transactional
    public void deleteSeats(Flight flight) {

        repository.deleteAllByFlight(flight);

    }
}
