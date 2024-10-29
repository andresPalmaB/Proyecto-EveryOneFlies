package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.repository.LocationRepository;
import com.betek.ms_flies.service.serviceInterface.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private final LocationRepository repository;

    @Override
    public Location createLocation(LocationDTO locationDTO) {

        if (repository.findLocationByCity(locationDTO.city().toLowerCase()).isPresent()){
            throw new ResourceNotFoundException(
                    "La Locacion con name " + locationDTO.city() + " ya existe en la base de datos."
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
                        "Aeropuerto por ID " + id + " no encontrada."));
    }

    @Override
    public Location getLocationByCity(String city) {

        return repository.findLocationByCity(city.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location por nombre ciudad " + city + " no encontrada."));
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
    public <T> DeleteResponse<T> deleteLocationById(Location location) {
        Location found = repository.findById(location.getIdLocation())
                .orElseThrow(() -> new ResourceNotFoundException(
                        location.getClass().getSimpleName() + " con ID " +
                                location.getIdLocation() + " no encontrado"));

        repository.delete(found);

        return new DeleteResponse<>(found.getClass().getSimpleName(), found.getCity());
    }
}
