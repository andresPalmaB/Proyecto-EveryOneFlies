package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.betek.everyOneFlies.model.Airport;

import java.util.List;

public interface AirportService {

    Airport createAirport(AirportDTO airportDTO);

    Airport getAirportById(Integer id);

    Airport getAirportByIataCode(String iataCode);

    Airport getAirportByName(String name);

    List<Airport> getAirportByCity(String city);

    List<Airport> getAirportsByCountry(String country);

    List<Airport> getAirports();

    Airport updateAirport(Airport airport);

    <T> DeleteResponse<T> deleteAirport(Airport airport);
}
