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
        return repository.save(
                new Location(
                        locationDTO.city(),
                        locationDTO.country(),
                        locationDTO.fechaInicioAlta(),
                        locationDTO.fechaFinAlta(),
                        locationDTO.fechaInicioMedia(),
                        locationDTO.fechaFinMedia(),
                        locationDTO.fechaInicioMedia2(),
                        locationDTO.fechaFinMedia2(),
                        locationDTO.precioBase()
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

        return repository.findByCity(city)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location por nombre ciudad " + city + " no encontrada."));
    }

    @Override
    public List<Location> getLocationByCountry(String country) {
        return repository.findByCountry(country);
    }

    @Override
    public List<Location> getLocation() {
        return repository.findAll();
    }

    @Override
    public <T> DeleteResponse<T> deleteById(Location geoData) {
        Location object = repository.findById(geoData.getIdLocation())
                .orElseThrow(() -> new ResourceNotFoundException(
                        geoData.getClass().getSimpleName() + " con ID " +
                                geoData.getIdLocation() + " no encontrado"));

        repository.delete(object);

        return new DeleteResponse<>(object.getClass().getSimpleName(), object.getCity());
    }
}
