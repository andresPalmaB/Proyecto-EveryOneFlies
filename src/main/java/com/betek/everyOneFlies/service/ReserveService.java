package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.*;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.repository.PassengerRepository;
import com.betek.everyOneFlies.repository.ReserveRepository;
import com.betek.everyOneFlies.service.serviceInterface.FlightService;
import com.betek.everyOneFlies.service.serviceInterface.TicketService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReserveService {

    @Autowired
    private final ReserveRepository reserveRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private TicketService ticketService;

    @PersistenceContext
    private EntityManager entityManager;

    public ReserveService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    @Transactional
    public Reserve createReserve(Reserve reserve) {

        Flight flight = flightService.getFlightByIdFlight(reserve.getFlightId());

        int contadorEconomic = 0;
        int contadorEconomicPremium = 0;
        int contadorBusiness = 0;
        int contadorFristClass = 0;
        List<String> list = new LinkedList<>();

        switch (reserve.getCategory()){
            case ECONOMYC -> {
                contadorEconomic = reserve.getPassengers().size();
                int fijo = 7 - flight.getEconomyCounter();
                for (int i = fijo; i < reserve.getPassengers().size() + fijo; i++){
                    list.add(i + flight.getFlightCode());
                }
            }
            case ECONOMYCPREMIUM -> {
                contadorEconomicPremium = reserve.getPassengers().size();
                int fijo = 4 - flight.getPremiumEconomyCounter();
                for (int i = fijo; i < reserve.getPassengers().size() + fijo; i++){
                    list.add(i + flight.getFlightCode());
                }
            }
            case BUSINESS -> {
                contadorBusiness = reserve.getPassengers().size();
                int fijo = 3 - flight.getBusinessCounter();
                for (int i = fijo; i < reserve.getPassengers().size() + fijo; i++){
                    list.add(i + flight.getFlightCode());
                }
            }
            case FIRST_CLASS -> {
                contadorFristClass = reserve.getPassengers().size();
                int fijo = 7 - flight.getFirstClassCounter();
                for (int i = fijo; i < reserve.getPassengers().size() + fijo; i++){
                    list.add(i + flight.getFlightCode());
                }
            }
        }

        flightService.updateSeatsSoldFlight(
                new FlightSeatSoldDTO(
                        flight.getFlightCode(),
                        contadorEconomic,
                        contadorEconomicPremium,
                        contadorBusiness,
                        contadorFristClass,
                        list
                )
        );

        entityManager.merge(reserve);

        return reserve;
    }

    public List<Reserve> getAllReserves() {
        return reserveRepository.findAll();
    }

    public Reserve getReserveById(Long id) {
        return reserveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
    }

    public Reserve updateReserve(Long id, Reserve updatedReserve) {
        Reserve reserve = getReserveById(id);
        reserve.setStatus(updatedReserve.getStatus());
        reserve.setPassengers(updatedReserve.getPassengers());
        return reserveRepository.save(reserve);
    }

    public Reserve updateReserveStatus(Long reservationId, ReservationStatus newStatus) {
        Reserve reserve = reserveRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserve.setStatus(newStatus);

        return reserveRepository.save(reserve);
    }

    public void createTicket(TicketDTO ticketDTO){
        if (ticketDTO.reserve().getStatus().equals(ReservationStatus.PAID)){
            ticketService.createTicket(ticketDTO);
        }
    }

    public void procesarTicket(Long ticketId) {
        ticketService.generateTicketPdf(ticketId);
    }

    public void updateTicket(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        ticketService.updateTicket(ticket);
    }

    public void deleteReserve(Long id) {
        Reserve reserve = getReserveById(id);
        reserveRepository.delete(reserve);
    }
}
