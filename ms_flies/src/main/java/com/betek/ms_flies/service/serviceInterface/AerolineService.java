package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AerolineaDTO;
import com.betek.ms_flies.model.Aerolinea;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface AerolineService {

    Aerolinea createAerolinea(AerolineaDTO aerolineaDTO);

    Aerolinea getById(Integer id);

    List<Aerolinea> getAerolineas();

    Aerolinea getAerolineaByName(AerolineaDTO aerolineaDTO);

    Aerolinea updateAerolinea(AerolineaDTO aerolineaDTO);

    <T> DeleteResponse<T> deleteById(AerolineaDTO aerolineaDTO);
}
