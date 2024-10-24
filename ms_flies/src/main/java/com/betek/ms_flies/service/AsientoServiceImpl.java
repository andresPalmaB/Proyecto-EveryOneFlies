package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AsientoDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Asiento;
import com.betek.ms_flies.repository.AsientoRepository;
import com.betek.ms_flies.service.serviceInterface.AsientoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AsientoServiceImpl implements AsientoService {

    @Autowired
    private AsientoRepository repository;

    @Override
    public Asiento createAsiento(AsientoDTO asientoDTO) {
        return repository.save(
                new Asiento(
                        asientoDTO.asientoDelVuelo(),
                        asientoDTO.disponible(),
                        asientoDTO.tipoAsiento()
                )
        );
    }

    @Override
    public List<Asiento> getAsientoDisponiblesEnVueloByTipo(AsientoDTO asientoDTO) {
        return repository.findByAsientoVueloAndTipoAsientoAndDisponible(
                asientoDTO.asientoDelVuelo(),
                asientoDTO.tipoAsiento(),
                asientoDTO.disponible()
        );
    }

    @Override
    public List<Asiento> getAsientoDisponiblesEnVuelo(AsientoDTO asientoDTO) {
        return repository.findByAsientoVueloAndDisponible(
                asientoDTO.asientoDelVuelo(),
                asientoDTO.disponible()
        );
    }

    @Override
    @Transactional
    public Asiento updateDisponibilidadAsiento(Asiento asiento) {
        Asiento encontrado = repository.findByAsientoVuelo(asiento.getAsientoVuelo())
                            .orElseThrow(()-> new ResourceNotFoundException(
                            "Asiento con codigo " + asiento.getAsientoVuelo() + " no encontrada."));

        encontrado.setDisponible(asiento.isDisponible());

        return repository.save(encontrado);
    }

    @Override
    public <T> DeleteResponse<T> deleteById(Asiento asiento) {
        Asiento object = repository.findById(asiento.getIdAsiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        asiento.getClass().getSimpleName() + " con ID " +
                                asiento.getIdAsiento() + " no encontrado"));

        repository.delete(asiento);

        return new DeleteResponse<>(object.getClass().getSimpleName(), object.getAsientoVuelo().toString());
    }
}
