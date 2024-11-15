package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.ReserveDTO;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.exception.ExistingResourceException;
import com.betek.everyOneFlies.exception.ValidateArguments;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.*;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.repository.PassengerRepository;
import com.betek.everyOneFlies.repository.ReserveRepository;
import com.betek.everyOneFlies.service.serviceInterface.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private TicketService ticketService;

    private boolean doesReserveExist(Seat seat) {
        return repository.existsReserveBySeat(seat);
    }

    @Override
    @Transactional
    public List<Reserve> createReserve(ReserveDTO reserveDTO) {

        List<Reserve> reservesList = new ArrayList<>();

        List<Seat> seatList = new ArrayList<>();

        int[] count = {0,0,0,0};

        if (reserveDTO.seatSoldDTO().seatCodes().size() != reserveDTO.passengerDTOList().size()){
            throw new IllegalArgumentException("The number of passenger does not match the number of seat codes.");
        }

        for (String seatCode : reserveDTO.seatSoldDTO().seatCodes()){
            Seat seat = seatService.getSeatBySeatCode(seatCode);
            switch (seat.getSeatCategory()){
                case ECONOMICAL -> count[0]++;
                case ECONOMICAL_PREMIUM -> count[1]++;
                case BUSINESS -> count[2]++;
                case FIRST_CLASS -> count[3]++;
            }
            seatList.add(seat);
        }

        if (count[0] != reserveDTO.seatSoldDTO().seatsSoldEconomy() ||
                count[1] != reserveDTO.seatSoldDTO().seatsSoldPremiumEconomy() ||
                count[2] != reserveDTO.seatSoldDTO().seatsSoldBusiness() ||
                count[3] != reserveDTO.seatSoldDTO().seatsSoldFirstClass()
        ){
            throw new IllegalArgumentException("The number of sold seats does not match the number of seat codes.");
        }

        if (!seatList.isEmpty()){
            if (reserveDTO.passengerDTOList().getFirst().isResponsibleForPayment().equals(false)){
                throw new IllegalArgumentException("The first passenger must be responsible for the payment.");
            }
            for (int i = 1; i < reserveDTO.passengerDTOList().size(); i++) {
                if (reserveDTO.passengerDTOList().get(i).isResponsibleForPayment().equals(true)){
                    throw new IllegalArgumentException("The first passenger must be responsible for the payment.");
                }
            }
            reserveDTO.passengerDTOList().forEach(passengerDTO -> {

            });
        }

        for (Seat seat : seatList){

            if (doesReserveExist(seat)){
                throw new ExistingResourceException("Reserve already exist.");
            }

            if (!seat.getAvailable()){
                throw new ResourceNotFoundException("Seat with code " + seat.getSeatCode() + " not available.");
            }

        }

        Flight flight = flightService.updateSeatsSoldFlight(reserveDTO.seatSoldDTO());
        String reserveCode = flight.getFlightCode() + "-R" + (repository.count() + 1);

        for (int i = 0; i < seatList.size(); i++) {

            seatService.updateSeatAvailability(seatList.get(i));

            Reserve reserve = new Reserve(
                    reserveDTO.reservationDate(),
                    flight,
                    seatList.get(i),
                    reserveCode
            );

            reservesList.add(reserve);
            repository.save(reserve);

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
    @Transactional
    public List<Reserve> updateReserveStatus(String reserveCode, String newStatus, LocalDateTime now) {

        ReservationStatus statusValidated = ValidateArguments.validateReservationStatus(newStatus);

        List<Reserve> reserveList = this.getReservesByReserveCode(reserveCode);

        List<Passenger> passengerList = new ArrayList<>();

        reserveList.forEach(reserve ->
                passengerList.add(passengerService.getPassengerByReserve(reserve))
        );

        switch (reserveList.getFirst().getStatus()){
            case PENDING -> {
                LocalDate today = now.toLocalDate();
                long daysBetween = ChronoUnit.DAYS.between(reserveList.getFirst().getReservationDate(), today);

                if (daysBetween >= 2){
                    reserveList.forEach(reserve -> {

                        reserve.setStatus(ReservationStatus.CANCELLED);

                        seatService.updateSeatAvailability(reserve.getSeat());
                        switch (reserve.getSeat().getSeatCategory()){
                            case ECONOMICAL -> reserve.getFlight().setEconomyCounter(reserve.getFlight().getEconomyCounter()+1);
                            case ECONOMICAL_PREMIUM -> reserve.getFlight().setPremiumEconomyCounter(reserve.getFlight().getPremiumEconomyCounter()+1);
                            case BUSINESS -> reserve.getFlight().setBusinessCounter(reserve.getFlight().getBusinessCounter()+1);
                            case FIRST_CLASS -> reserve.getFlight().setFirstClassCounter(reserve.getFlight().getFirstClassCounter()+1);
                        }

                    });
                }

                if (statusValidated.equals(ReservationStatus.CANCELLED)
                        || statusValidated.equals(ReservationStatus.PAID)){

                    reserveList.forEach(reserve -> reserve.setStatus(statusValidated));

                    if (statusValidated.equals(ReservationStatus.CANCELLED)){

                        reserveList.forEach(reserve -> {
                            seatService.updateSeatAvailability(reserve.getSeat());
                            switch (reserve.getSeat().getSeatCategory()){
                                case ECONOMICAL -> reserve.getFlight().setEconomyCounter(reserve.getFlight().getEconomyCounter()+1);
                                case ECONOMICAL_PREMIUM -> reserve.getFlight().setPremiumEconomyCounter(reserve.getFlight().getPremiumEconomyCounter()+1);
                                case BUSINESS -> reserve.getFlight().setBusinessCounter(reserve.getFlight().getBusinessCounter()+1);
                                case FIRST_CLASS -> reserve.getFlight().setFirstClassCounter(reserve.getFlight().getFirstClassCounter()+1);
                            }
                        });
                    }

                } else {
                    throw new IllegalArgumentException("Reservation status sent: " + statusValidated + ", Reservation status expected: " + ReservationStatus.CANCELLED + " || " + ReservationStatus.PAID);
                }
            }
            case PAID -> {
                if (statusValidated.equals(ReservationStatus.CANCELLED_PAID)
                        || statusValidated.equals(ReservationStatus.CONFIRMED)){

                    reserveList.forEach(reserve -> reserve.setStatus(statusValidated));

                    if (statusValidated.equals(ReservationStatus.CANCELLED_PAID)){
                        reserveList.forEach(reserve -> {
                            seatService.updateSeatAvailability(reserve.getSeat());
                            switch (reserve.getSeat().getSeatCategory()){
                                case ECONOMICAL -> reserve.getFlight().setEconomyCounter(reserve.getFlight().getEconomyCounter()+1);
                                case ECONOMICAL_PREMIUM -> reserve.getFlight().setPremiumEconomyCounter(reserve.getFlight().getPremiumEconomyCounter()+1);
                                case BUSINESS -> reserve.getFlight().setBusinessCounter(reserve.getFlight().getBusinessCounter()+1);
                                case FIRST_CLASS -> reserve.getFlight().setFirstClassCounter(reserve.getFlight().getFirstClassCounter()+1);
                            }
                        });
                    }


                    if (statusValidated.equals(ReservationStatus.CONFIRMED)){

                        passengerList.forEach(passenger ->
                            ticketService.createTicket(new TicketDTO(passenger, now.toLocalDate()))
                        );

                    }

                } else {
                    throw new IllegalArgumentException("Reservation status sent: " + statusValidated + ", Reservation status expected: " + ReservationStatus.CANCELLED_PAID + " || " + ReservationStatus.CONFIRMED);
                }
            }
            case CANCELLED_PAID -> {
                if (statusValidated.equals(ReservationStatus.REFUNDED)){
                    reserveList.forEach(reserve -> reserve.setStatus(statusValidated));
                } else {
                    throw new IllegalArgumentException("Reservation status sent: " + statusValidated + ", Reservation status expected: " + ReservationStatus.REFUNDED);
                }
            }
            case CONFIRMED -> {
                if (statusValidated.equals(ReservationStatus.COMPLETED)
                        || statusValidated.equals(ReservationStatus.NO_SHOW)){

                    if (statusValidated.equals(ReservationStatus.COMPLETED)){
                        reserveList.forEach(reserve -> reserve.setStatus(statusValidated));
                        passengerList.forEach(passenger -> {
                            Ticket ticket = ticketService.getTicketByPassenger(passenger);
                            ticket.setTicketStatus(TicketStatus.COMPLETED);
                        });
                    }

                    if (statusValidated.equals(ReservationStatus.NO_SHOW)){
                        reserveList.forEach(reserve -> reserve.setStatus(statusValidated));
                        passengerList.forEach(passenger -> {
                            Ticket ticket = ticketService.getTicketByPassenger(passenger);
                            ticket.setTicketStatus(TicketStatus.NO_SHOW);
                        });
                    }
                } else {
                    throw new IllegalArgumentException("Reservation status sent: " + statusValidated + ", Reservation status expected: " + ReservationStatus.COMPLETED + " || " + ReservationStatus.NO_SHOW);
                }
            }
        }

        return repository.saveAll(reserveList);
    }

}
