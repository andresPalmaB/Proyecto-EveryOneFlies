package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AeropuertoDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Aeropuerto;
import com.betek.ms_flies.model.Location;
import com.betek.ms_flies.repository.AeropuestoRepository;
import com.betek.ms_flies.service.serviceInterface.AeropuertoService;
import com.betek.ms_flies.service.serviceInterface.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Repository
public class AeropuertoServiceImpl implements AeropuertoService {

    @Autowired
    private AeropuestoRepository repository;

    @Autowired
    private LocationService locationService;

    @Override
    public Aeropuerto createAeropuerto(AeropuertoDTO aeropuertoDTO) {

        Location location = locationService.getLocationById(aeropuertoDTO.idLocation());

        return repository.save(
                new Aeropuerto(
                        aeropuertoDTO.codigoIATA(),
                        aeropuertoDTO.nombreAeropuerto(),
                        location
                )
        );
    }

    @Override
    public Aeropuerto getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aeropuerto por ID " + id + " no encontrada."));
    }

    @Override
    public Aeropuerto getCodigoATA(AeropuertoDTO aeropuertoDTO) {
        return repository.findByCodigoIATA(aeropuertoDTO.codigoIATA())
                .orElseThrow(()-> new ResourceNotFoundException(
                "Aeropuerto por CodigoIATA " + aeropuertoDTO.codigoIATA() + " no encontrada."));
    }

    @Override
    public Aeropuerto getNombreAeropuerto(AeropuertoDTO aeropuertoDTO) {
        return repository.findByNombreAeropuerto(aeropuertoDTO.nombreAeropuerto())
                         .orElseThrow(()-> new ResourceNotFoundException(
                                    "Aeropuerto por Nombre " + aeropuertoDTO.nombreAeropuerto() + " no encontrada."));
    }

    @Override
    public List<Aeropuerto> getCity(AeropuertoDTO aeropuertoDTO) {

        Location location = locationService.getLocationById(aeropuertoDTO.idLocation());

        return repository.findByLocation(location);
    }

    @Override
    public List<Aeropuerto> getCountry(AeropuertoDTO aeropuertoDTO) {

        Location location = locationService.getLocationById(aeropuertoDTO.idLocation());

        return repository.findByLocationIn(locationService.getLocationByCountry(location.getCountry()));
    }

    @Override
    public List<Aeropuerto> getAeropuestos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Aeropuerto updateAeropuerto(AeropuertoDTO aeropuertoDTO) {

        Aeropuerto aeropuerto = this.getCity(aeropuertoDTO).getFirst();

        Aeropuerto found = repository.findById(aeropuerto.getIdAeropuerto())
                        .orElseThrow(()-> new ResourceNotFoundException(
                        "Aeropuerto con codigo " + aeropuerto.getIdAeropuerto() + " no encontrada."));

        found.setNombreAeropuerto(aeropuerto.getNombreAeropuerto());

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteById(AeropuertoDTO aeropuertoDTO) {

        Aeropuerto aeropuerto = getCity(aeropuertoDTO).getFirst();

        Aeropuerto object = repository.findById(aeropuerto.getIdAeropuerto())
                .orElseThrow(() -> new ResourceNotFoundException(
                        aeropuerto.getClass().getSimpleName() + " con ID " +
                                aeropuerto.getIdAeropuerto() + " no encontrado"));

        repository.delete(aeropuerto);

        return new DeleteResponse<>(object.getClass().getSimpleName(), object.getNombreAeropuerto());
    }
}
