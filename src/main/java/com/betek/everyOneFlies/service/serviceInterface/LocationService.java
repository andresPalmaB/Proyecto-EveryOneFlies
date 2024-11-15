package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.LocationDTO;
import com.betek.everyOneFlies.model.Location;

import java.util.List;

public interface LocationService {

    Location createLocation(LocationDTO locationDTO);

    Location getLocationById(Integer id);

    Location getLocationByCity(String city);

    List<Location> getLocationByCountry(String country);

    List<Location> getLocations();

    Location updateLocationDate(Location location);

    Location updateLocationBasePrice(Location location);

    <T> DeleteResponse<T> deleteLocation(Location geoData);
}
