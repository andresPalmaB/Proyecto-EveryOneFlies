package com.betek.ms_flies.service.flightService;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.OutBoundRoute;
import com.betek.ms_flies.dto.ReturnRoute;
import com.betek.ms_flies.dto.dtoModel.FlightDTO;
import com.betek.ms_flies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.ms_flies.dto.dtoModel.SeatDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Airline;
import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;
import com.betek.ms_flies.repository.FlightRepository;
import com.betek.ms_flies.service.serviceInterface.AirlineService;
import com.betek.ms_flies.service.serviceInterface.AirportService;
import com.betek.ms_flies.service.serviceInterface.FlightService;
import com.betek.ms_flies.service.serviceInterface.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository repository;

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private SeatService seatService;

    @Override
    public boolean doesFlightExist(Airline airline,
                                   Airport origin,
                                   Airport destination,
                                   LocalDate departureDate,
                                   LocalTime departureTime) {
        return repository.existsByAirlineAndOriginAndDestinationAndDepartureDateAndDepartureTime(
                airline, origin, destination, departureDate, departureTime
        );
    }

    @Override
    public Flight createFlight(FlightDTO flightDTO) {

        Airline airline = airlineService.getAirlineByName(flightDTO.airlineDTO());

        Airport originAirport = airportService.getAirportByIataCode(flightDTO.originAirportDTO());

        Airport destinationAirport = airportService.getAirportByIataCode(flightDTO.destinationAirportDTO());

        if (doesFlightExist(airline, originAirport, destinationAirport, flightDTO.departureDate(), flightDTO.departureTime())) {
            throw new ResourceNotFoundException("Este vuelo ya existe.");
        }

        Flight flight = repository.save(
                new Flight(
                        airline,
                        originAirport,
                        destinationAirport,
                        flightDTO.departureDate(),
                        flightDTO.departureTime(),
                        flightDTO.arrivalDate(),
                        flightDTO.arrivalTime(),
                        flightDTO.economyCounter(),
                        flightDTO.premiumEconomyCounter(),
                        flightDTO.businessCounter(),
                        flightDTO.firstClassCounter()
                )
        );

        int seatNumber = 1;
        int economyLimit = flight.getEconomyCounter();
        int premiumLimit = economyLimit + flight.getPremiumEconomyCounter();
        int businessLimit = premiumLimit + flight.getBusinessCounter();
        int totalSeats = flight.getTotalSeats();

        for (int i = 0; i < totalSeats; i++) {

            TipoAsiento tipoAsiento;

            if (i < economyLimit){
                tipoAsiento = TipoAsiento.ECONOMYC;
            } else if (i < premiumLimit){
                tipoAsiento = TipoAsiento.ECONOMYCPREMIUM;
            } else if (i < businessLimit) {
                tipoAsiento = TipoAsiento.BUSINESS;
            } else {
                tipoAsiento = TipoAsiento.FIRST_CLASS;
            }

            seatService.createSeat(
                    new SeatDTO(flight.getFlightCode(),
                            true,
                            tipoAsiento),
                    seatNumber,
                    flight
            );

            seatNumber++;
        }

        return flight;
    }

    @Override
    public Flight getFlightByFlightCode(String flightCode) {
        return repository.findFlightByFlightCode(flightCode.toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Vuelo con flightCode " + flightCode + " no encontrada."));
    }

    @Override
    public List<Flight> getRouteByDate(OutBoundRoute route) {
        return repository.findFlightByDepartureDateAndOriginAndDestinationOrderByFlightCodeAsc(
                route.departureDate(),
                airportService.getAirportByIataCode(route.aeropuertoOrigenDTO()),
                airportService.getAirportByIataCode(route.aeropuertoDestinoDTO())
        );
    }

    @Override
    public List<Flight> getRutaByRangoFechas(ReturnRoute route) {
        return repository.findFlightByDepartureDateBetweenAndOriginAndDestinationOrderByFlightCodeAsc(
                route.departureDate(),
                route.returnDate(),
                airportService.getAirportByIataCode(route.aeropuertoOrigenDTO()),
                airportService.getAirportByIataCode(route.aeropuertoDestinoDTO())
        );
    }

    @Override
    public List<Seat> getSeatAvailablebySeatType(SeatDTO seatDTO) {

        Flight flight = this.getFlightByFlightCode(seatDTO.flightCode());

        return seatService.getSeatAvailabilityInFlightBySeatType(seatDTO, flight);
    }

    @Override
    public List<Seat> getSeatAvailableFromFlight(String flightCode) {

        Flight flight = this.getFlightByFlightCode(flightCode);

        return seatService.getSeatAvailabilityInFlight(flight);
    }

    @Override
    public Double getFlightCost(TipoAsiento tipo, String flightCode) {

        Flight flight = this.getFlightByFlightCode(flightCode);

        Airport airport = flight.getDestination();

        return FlightCalculateCost.Calculate(tipo, airport.getLocation(), flight.getDepartureDate(), flight);
    }

    @Override
    @Transactional
    public Flight updateFlightDateTime(Flight flight) {
        Flight found = repository.findById(flight.getIdFlight())
                .orElseThrow(() -> new ResourceNotFoundException(
                        flight.getClass().getSimpleName() + " con ID " +
                                flight.getIdFlight() + " no encontrado"));

        found.setDepartureDate(flight.getDepartureDate());
        found.setDepartureTime(flight.getDepartureTime());
        found.setArrivalDate(flight.getArrivalDate());
        found.setArrivalTime(flight.getArrivalTime());

        return repository.save(found);
    }

    @Override
    public Flight updateSeatsSoldFlight(FlightSeatSoldDTO seatsSoldDTO) {

        Flight found = this.getFlightByFlightCode(seatsSoldDTO.flightCode());

        found.setEconomyCounter(
                found.getEconomyCounter() - seatsSoldDTO.seatsSoldEconomy()
        );

        found.setPremiumEconomyCounter(
                found.getPremiumEconomyCounter() - seatsSoldDTO.seatsSoldPremiumEconomy()
        );

        found.setBusinessCounter(
                found.getBusinessCounter() - seatsSoldDTO.seatsSoldBusiness()
        );

        found.setFirstClassCounter(
                found.getFirstClassCounter() - seatsSoldDTO.seatsSoldFirstClass()
        );

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteFlightById(Flight flight) {
        Flight object = repository.findById(flight.getIdFlight())
                .orElseThrow(() -> new ResourceNotFoundException(
                        flight.getClass().getSimpleName() + " con ID " +
                                flight.getIdFlight() + " no encontrado"));

        repository.delete(flight);

        return new DeleteResponse<>(object.getClass().getSimpleName(), flight.getFlightCode());
    }
}
