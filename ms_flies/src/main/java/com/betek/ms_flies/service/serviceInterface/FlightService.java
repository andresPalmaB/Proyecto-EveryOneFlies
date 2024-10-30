package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.OutBoundRoute;
import com.betek.ms_flies.dto.ReturnRoute;
import com.betek.ms_flies.dto.dtoModel.FlightDTO;
import com.betek.ms_flies.dto.dtoModel.FlightSeatSoldDTO;
import com.betek.ms_flies.dto.dtoModel.SeatDTO;
import com.betek.ms_flies.model.Airline;
import com.betek.ms_flies.model.Airport;
import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;

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

    Flight getFlightByFlightCode(String flightCode);

    List<Flight> getRouteByDate(OutBoundRoute route);

    List<Flight> getRutaByRangoFechas(ReturnRoute route);

    List<Seat> getSeatAvailablebySeatType(SeatDTO seatDTO);

    List<Seat> getSeatAvailableFromFlight(String flightCode);

    Double getFlightCost(TipoAsiento tipo, String flightCode);

    Flight updateFlightDateTime(Flight vuelo);

    Flight updateSeatsSoldFlight(FlightSeatSoldDTO asientoVendidoDTO);

    <T> DeleteResponse<T> deleteFlightById(Flight vuelo);

}
