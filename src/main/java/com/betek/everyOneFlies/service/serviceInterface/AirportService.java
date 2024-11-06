package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.AirportDTO;
import com.betek.everyOneFlies.model.Airport;

import java.util.List;

public interface AirportService {

    Airport createAirport(AirportDTO airportDTO);

    Airport getAirportById(Integer id);

    Airport getAirportByIataCode(AirportDTO airportDTO);

    Airport getAirportByName(AirportDTO airportDTO);

    List<Airport> getAirportByCity(AirportDTO airportDTO);

    List<Airport> getAirportsByCountry(String country);

    List<Airport> getAirports();

    Airport updateAirportByIataCode(Airport airport);

    <T> DeleteResponse<T> deleteAirportByIataCode(Airport airport);
}
