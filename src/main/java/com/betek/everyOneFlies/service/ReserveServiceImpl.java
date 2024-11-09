package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.ReserveDTO;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.*;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.repository.PassengerRepository;
import com.betek.everyOneFlies.repository.ReserveRepository;
import com.betek.everyOneFlies.service.serviceInterface.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private final ReserveRepository repository;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private SeatService seatService;

    private boolean doesReserveExist(Seat seat) {
        return repository.existsReserveBySeat(seat);
    }

    @Override
    public List<Reserve> createReserve(ReserveDTO reserveDTO) {

        List<Reserve> reservesList = new ArrayList<>();

        List<Seat> seatList = new ArrayList<>();

        for (String seatCode : reserveDTO.seatSoldDTO().seatCodes()){

            Seat seat = seatService.getSeatBySeatCode(seatCode);

            if (doesReserveExist(seat)){
                throw new ResourceNotFoundException("Reserve already exist.");
            }

            if (!seat.getAvailable()){
                throw new ResourceNotFoundException("Seat with code " + seatCode + " not available.");
            }

            seatList.add(seat);

        }

        seatList.forEach(seat -> seatService.updateSeatAvailability(seat));
        Flight flight = flightService.updateSeatsSoldFlight(reserveDTO.seatSoldDTO());
        String reserveCode = flight.getFlightCode() + "-R" + (repository.count() + 1);

        for (int i = 0; i < seatList.size(); i++) {

            Reserve reserve = new Reserve(
                    reserveDTO.reservationDate(),
                    flight,
                    seatList.get(i),
                    reserveCode
            );

            repository.save(reserve);
            reservesList.add(reserve);

            passengerService.createPassenger(reserveDTO.passengerDTOList().get(i), reserve);

        }

        return reservesList;
    }

    @Override
    public List<Reserve> getReservesFromFlight(String flightCode) {

        Flight found = flightService.getFlightByFlightCode(flightCode);

        return repository.findReserveByFlight(found);
    }

    @Override
    public Reserve getReserveById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation with id " + id + "not found."));
    }

    @Override
    public List<Reserve> getReservesByReserveCode(String reserveCode) {
        return repository.findReserveByReserveCode(reserveCode);
    }

    @Override
    public List<Reserve> updateReserveStatus(String reserveCode, ReservationStatus newStatus) {

        List<Reserve> reserveList = repository.findReserveByReserveCode(reserveCode);

        reserveList.forEach(reserve -> reserve.setStatus(newStatus));

        return repository.saveAll(reserveList);
    }

    @Override
    public <T> DeleteResponse<T> deleteReserve(Reserve reserve) {

        Reserve found = this.getReserveById(reserve.getIdReserve());

        if (!found.equals(reserve)){
            throw new ResourceNotFoundException(
                    "The reserve data does not match the database. Please check and try again."
            );
        }

        Reserve saved = new Reserve();
        saved.setIdReserve(found.getIdReserve());

        repository.delete(found);

        return new DeleteResponse<>(saved.getClass().getSimpleName(), saved.getIdReserve().toString());
    }

    @Override
    public void deleteReserves(String reserveCode) {

        List<Reserve> found = this.getReservesByReserveCode(reserveCode);
        repository.deleteAll(found);

    }
}
