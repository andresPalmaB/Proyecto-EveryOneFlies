package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import com.betek.ms_flies.model.Location;

import java.util.List;

public interface LocationService {

    Location createLocation(LocationDTO locationDTO);

    Location getLocationById(Integer id);

    Location getLocationByCity(String city);

    List<Location> getLocationByCountry(String country);

    List<Location> getLocation();

    <T> DeleteResponse<T> deleteById(Location geoData);
}
