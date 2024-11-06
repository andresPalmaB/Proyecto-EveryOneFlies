package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.LocationDTO;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Location;
import com.betek.everyOneFlies.repository.LocationRepository;
import com.betek.everyOneFlies.service.serviceInterface.LocationService;
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

        Location save = new Location();
        save.setCity(found.getCity());

        repository.delete(found);

        return new DeleteResponse<>(save.getClass().getSimpleName(), save.getCity());
    }
}
