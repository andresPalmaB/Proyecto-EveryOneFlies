package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AirportDTO;
import com.betek.ms_flies.model.Airport;

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
