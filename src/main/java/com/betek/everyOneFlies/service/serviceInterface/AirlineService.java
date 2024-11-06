package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import com.betek.everyOneFlies.model.Airline;

import java.util.List;

public interface AirlineService {

    Airline createAirline(AirlineDTO airlineDTO);

    Airline getAirlineById(Integer id);

    List<Airline> getAirlines();

    Airline getAirlineByName(AirlineDTO airlineDTO);

    Airline updateAirline(Airline airline);

    <T> DeleteResponse<T> deleteAirlineById(Airline airline);

}
