package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.OutBoundRoute;
import com.betek.everyOneFlies.dto.ReturnRoute;
import com.betek.everyOneFlies.dto.dtoModel.FlightDTO;
import com.betek.everyOneFlies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.everyOneFlies.dto.dtoModel.SeatDTO;
import com.betek.everyOneFlies.model.Airline;
import com.betek.everyOneFlies.model.Airport;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.model.modelEnum.SeatCategory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FlightService {

    boolean doesFlightExist(Airline airline,
                            Airport origin,
                            Airport destination,
                            LocalDate departureDate,
                            LocalTime departureTime);

    Flight createFlight(FlightDTO flightDTO);

    Flight getFlightByIdFlight(Long idFlight);

    Flight getFlightByFlightCode(String flightCode);

    List<Flight> getRouteByDate(OutBoundRoute route);

    List<Flight> getRutaByRangoFechas(ReturnRoute route);

    List<Seat> getSeatAvailableBySeatTypeFromFlight(SeatDTO seatDTO);

    List<Seat> getSeatAvailableFromFlight(String flightCode);

    Double getFlightCost(SeatCategory tipo, String flightCode);

    Flight updateFlightDateTime(Flight vuelo);

    Flight updateSeatsSoldFlight(FlightSeatSoldDTO asientoVendidoDTO);

    <T> DeleteResponse<T> deleteFlightById(Flight vuelo);

}
