package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AerolineaDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Aerolinea;
import com.betek.ms_flies.repository.AerolineaRepository;
import com.betek.ms_flies.service.serviceInterface.AerolineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AerolineaServiceImpl implements AerolineService {

    @Autowired
    private final AerolineaRepository repository;

    @Override
    public Aerolinea createAerolinea(AerolineaDTO aerolineaDTO) {
        return repository.save(new Aerolinea(aerolineaDTO.nombre(), aerolineaDTO.siglas()));
    }

    @Override
    public Aerolinea getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aerolinea con ID " + id + " no encontrada."));
    }

    @Override
    public List<Aerolinea> getAerolineas() {
        return repository.findAll();
    }

    @Override
    public Aerolinea getAerolineaByName(AerolineaDTO aerolineaDTO) {
        return repository.findByNombre(aerolineaDTO.nombre())
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Aerolinea por nombre " + aerolineaDTO.nombre() + " no encontrada."));
    }

    @Override
    @Transactional
    public Aerolinea updateAerolinea(AerolineaDTO aerolineaDTO) {

        Aerolinea aerolinea = this.getAerolineaByName(aerolineaDTO);

        Aerolinea aerolineaEncontrada = repository.findById(aerolinea.getIdAerolinea())
                                        .orElseThrow(()-> new ResourceNotFoundException(
                                        "Aerolinea con ID " + aerolinea.getIdAerolinea() + " no encontrada."));

        aerolineaEncontrada.setNombre(aerolinea.getNombre());
        aerolineaEncontrada.setSiglas(aerolinea.getSiglas());

        repository.save(aerolineaEncontrada);

        return aerolineaEncontrada;
    }

    @Override
    public <T> DeleteResponse<T> deleteById(AerolineaDTO aerolineaDTO) {

        Aerolinea aerolinea = this.getAerolineaByName(aerolineaDTO);

        Aerolinea object = repository.findById(aerolinea.getIdAerolinea())
                .orElseThrow(() -> new ResourceNotFoundException(
                        aerolinea.getClass().getSimpleName() + " con ID " +
                                aerolinea.getIdAerolinea() + " no encontrado"));

        repository.delete(aerolinea);

        return new DeleteResponse<>(object.getClass().getSimpleName(), object.getNombre());
    }
}

