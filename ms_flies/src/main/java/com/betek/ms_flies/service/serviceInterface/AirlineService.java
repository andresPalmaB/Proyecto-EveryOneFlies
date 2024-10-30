package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirlineDTO;
import com.betek.ms_flies.model.Airline;

import java.util.List;

public interface AirlineService {

    Airline createAirline(AirlineDTO airlineDTO);

    Airline getAirlineById(Integer id);

    List<Airline> getAirlines();

    Airline getAirlineByName(AirlineDTO airlineDTO);

    Airline updateAirline(Airline airline);

    <T> DeleteResponse<T> deleteAirlineById(Airline airline);

}
