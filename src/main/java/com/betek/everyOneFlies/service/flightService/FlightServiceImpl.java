package com.betek.everyOneFlies.service.flightService;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.OutBoundRoute;
import com.betek.everyOneFlies.dto.ReturnRoute;
import com.betek.everyOneFlies.dto.dtoModel.FlightDTO;
import com.betek.everyOneFlies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.exception.ExistingResourceException;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;
import com.betek.everyOneFlies.repository.FlightRepository;
import com.betek.everyOneFlies.service.serviceInterface.AirlineService;
import com.betek.everyOneFlies.service.serviceInterface.AirportService;
import com.betek.everyOneFlies.service.serviceInterface.FlightService;
import com.betek.everyOneFlies.service.serviceInterface.SeatService;
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
        return repository.existsFlightByAirlineAndOriginAndDestinationAndDepartureDateAndDepartureTime(
                airline, origin, destination, departureDate, departureTime
        );
    }

    @Override
    public Flight createFlight(FlightDTO flightDTO) {

        Airline airline = airlineService.getAirlineByName(flightDTO.airlineName());

        Airport originAirport = airportService.getAirportByIataCode(flightDTO.iataCodeOriginAirport());

        Airport destinationAirport = airportService.getAirportByIataCode(flightDTO.iataCodeDestinationAirport());

        if (doesFlightExist(
                airline,
                originAirport,
                destinationAirport,
                flightDTO.departureDate(),
                flightDTO.departureTime())) {
            throw new ExistingResourceException("Flight already exist.");
        }

        Flight flight = new Flight(
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
        );

        flight.setFlightCode("TEMP");
        flight = repository.save(flight);

        int seatNumber = 1;
        int economyLimit = flight.getEconomyCounter();
        int premiumLimit = economyLimit + flight.getPremiumEconomyCounter();
        int businessLimit = premiumLimit + flight.getBusinessCounter();
        int totalSeats = flight.getTotalSeats();

        for (int i = 0; i < totalSeats; i++) {

            SeatCategory seatCategory;

            if (i < economyLimit){
                seatCategory = SeatCategory.ECONOMICAL;
            } else if (i < premiumLimit){
                seatCategory = SeatCategory.ECONOMICAL_PREMIUM;
            } else if (i < businessLimit) {
                seatCategory = SeatCategory.BUSINESS;
            } else {
                seatCategory = SeatCategory.FIRST_CLASS;
            }

            seatService.createSeat(
                    new SeatDTO(flight.getFlightCode(),
                            seatCategory.toString()),
                    seatNumber,
                    flight
            );

            seatNumber++;
        }

        return flight;
    }

    @Override
    public Flight getFlightByIdFlight(Long idFlight) {
        return repository.findById(idFlight)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Flight with ID " + idFlight + " not found."
                        )
                );
    }

    @Override
    public Flight getFlightByFlightCode(String flightCode) {
        return repository.findFlightByFlightCode(flightCode.toLowerCase())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Flight with Code " + flightCode + " not found."));
    }

    @Override
    public List<Flight> getRouteByDate(OutBoundRoute route) {
        return repository.findFlightByDepartureDateAndOriginAndDestinationOrderByFlightCodeAsc(
                LocalDate.parse(route.departureDate()),
                airportService.getAirportByIataCode(route.iataCodeOriginAirport()),
                airportService.getAirportByIataCode(route.iataCodeDestinationAirport())
        );
    }

    @Override
    public List<Flight> getRutaByRangoFechas(ReturnRoute route) {
        return repository.findFlightByDepartureDateBetweenAndOriginAndDestinationOrderByFlightCodeAsc(
                LocalDate.parse(route.departureDate()),
                LocalDate.parse(route.returnDate()),
                airportService.getAirportByIataCode(route.iataCodeOriginAirport()),
                airportService.getAirportByIataCode(route.iataCodeDestinationAirport())
        );
    }

    @Override
    public List<Seat> getSeatAvailableBySeatTypeFromFlight(SeatDTO seatDTO) {

        Flight flight = this.getFlightByFlightCode(seatDTO.flightCode());

        return seatService.getSeatAvailabilityInFlightBySeatType(seatDTO, flight);
    }

    @Override
    public List<Seat> getSeatAvailableFromFlight(String flightCode) {

        Flight flight = this.getFlightByFlightCode(flightCode);

        return seatService.getSeatAvailabilityInFlight(flight);
    }

    @Override
    public Double getFlightCost(SeatDTO seatDTO) {

        Flight flight = this.getFlightByFlightCode(seatDTO.flightCode());

        Airport airport = flight.getDestination();

        return FlightCalculateCost.Calculate(SeatCategory.valueOf(seatDTO.seatCategory()), airport.getLocation(), flight.getDepartureDate(), flight);
    }

    @Override
    @Transactional
    public Flight updateFlightDateTime(Flight flight) {
        Flight found = repository.findById(flight.getIdFlight())
                .orElseThrow(() -> new ResourceNotFoundException(
                        flight.getClass().getSimpleName() + " with ID " +
                                flight.getIdFlight() + " not found."));

        found.setDepartureDate(flight.getDepartureDate());
        found.setDepartureTime(flight.getDepartureTime());
        found.setArrivalDate(flight.getArrivalDate());
        found.setArrivalTime(flight.getArrivalTime());

        return repository.save(found);
    }

    @Override
    public Flight updateSeatsSoldFlight(FlightSeatSoldDTO seatsSoldDTO) {

        Flight found = this.getFlightByFlightCode(seatsSoldDTO.flightCode().toLowerCase());

        validateSale(seatsSoldDTO, found);

        found.setEconomyCounter(found.getEconomyCounter() - seatsSoldDTO.seatsSoldEconomy());
        found.setPremiumEconomyCounter(found.getPremiumEconomyCounter() - seatsSoldDTO.seatsSoldPremiumEconomy());
        found.setBusinessCounter(found.getBusinessCounter() - seatsSoldDTO.seatsSoldBusiness());
        found.setFirstClassCounter(found.getFirstClassCounter() - seatsSoldDTO.seatsSoldFirstClass());

        return repository.save(found);

    }

    private static void validateSale(FlightSeatSoldDTO seatsSoldDTO, Flight found) {

        Integer[] list1 = {
                seatsSoldDTO.seatsSoldEconomy(),
                seatsSoldDTO.seatsSoldPremiumEconomy(),
                seatsSoldDTO.seatsSoldBusiness(),
                seatsSoldDTO.seatsSoldFirstClass()
        };

        Integer[] list2 = {
                found.getEconomyCounter(),
                found.getPremiumEconomyCounter(),
                found.getBusinessCounter(),
                found.getFirstClassCounter()
        };

        SeatCategory[] categories = {
                SeatCategory.ECONOMICAL,
                SeatCategory.ECONOMICAL_PREMIUM,
                SeatCategory.BUSINESS,
                SeatCategory.FIRST_CLASS
        };

        for (int i = 0; i < 4; i++) {

            int rest = list2[i] - list1[i];

            if (rest < 0){
                throw new ResourceNotFoundException(
                        "You cannot purchase more seats for " + categories[i]);
            }

        }

    }

    @Override
    @Transactional
    public <T> DeleteResponse<T> deleteFlightById(Flight flight) {

        Flight found = this.getFlightByIdFlight(flight.getIdFlight());

        if (!found.equals(flight)){
            throw new ResourceNotFoundException(
                    "The flight data does not match the database. Please check and try again."
            );
        }

        Flight saved = new Flight();
        saved.setFlightCode(flight.getFlightCode());

        repository.delete(flight);

        return new DeleteResponse<>(saved.getClass().getSimpleName(), saved.getFlightCode());

    }
}
