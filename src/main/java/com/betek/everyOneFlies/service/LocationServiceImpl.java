package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.LocationDTO;
import com.betek.everyOneFlies.exception.ExistingResourceException;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.repository.LocationRepository;
import com.betek.everyOneFlies.service.serviceInterface.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private final LocationRepository repository;

    @Override
    public Location createLocation(LocationDTO locationDTO) {

        if (repository.findLocationByCity(locationDTO.city().toLowerCase()).isPresent()){
            throw new ExistingResourceException(
                    "The Location with the name " + locationDTO.city() + " already exists in the database."
            );
        }

        return repository.save(
                new Location(
                        locationDTO
                )
        );
    }

    @Override
    public Location getLocationById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Location with ID " + id + " not found."));
    }

    @Override
    public Location getLocationByCity(String city) {

        return repository.findLocationByCity(city.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location by city " + city + " not found."));
    }

    @Override
    public List<Location> getLocationByCountry(String country) {
        return repository.findLocationByCountry(country.toLowerCase());
    }

    @Override
    public List<Location> getLocations() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Location updateLocationDate(Location location) {

        Location found = this.getLocationById(location.getIdLocation());

        found.setStartDateH(location.getStartDateH());
        found.setStartDateH2(location.getStartDateH2());
        found.setEndDateH(location.getEndDateH());
        found.setEndDateH2(location.getEndDateH2());

        found.setStartDateM(location.getStartDateM());
        found.setStartDateM2(location.getStartDateM2());
        found.setEndDateM(location.getEndDateM());
        found.setEndDateM2(location.getEndDateM2());

        return repository.save(found);
    }

    @Override
    @Transactional
    public Location updateLocationBasePrice(Location location) {

        Location found = this.getLocationById(location.getIdLocation());

        found.setBasePrice(location.getBasePrice());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteLocation(Location location) {

        Location found = this.getLocationById(location.getIdLocation());

        if (!found.equals(location)){
            throw new ResourceNotFoundException(
                    "The location data does not match the database. Please check and try again."
            );
        }

        Location save = new Location();
        save.setCity(found.getCity());

        repository.delete(found);

        return new DeleteResponse<>(save.getClass().getSimpleName(), save.getCity());
    }
}
