package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AsientoDTO;
import com.betek.ms_flies.model.Asiento;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;

import java.util.List;

public interface AsientoService {

    Asiento createAsiento(AsientoDTO asientoDTO);

    List<Asiento> getAsientoDisponiblesEnVueloByTipo(AsientoDTO asientoDTO);

    List<Asiento> getAsientoDisponiblesEnVuelo(AsientoDTO asientoDTO);

    Asiento updateDisponibilidadAsiento(Asiento asiento);

    <T> DeleteResponse<T> deleteById(Asiento asiento);
}
