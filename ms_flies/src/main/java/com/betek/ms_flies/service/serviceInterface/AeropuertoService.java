package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.dtoModel.AeropuertoDTO;
import com.betek.ms_flies.model.Aeropuerto;

import java.util.List;

public interface AeropuertoService {

    Aeropuerto createAeropuerto(AeropuertoDTO aeropuertoDTO);

    Aeropuerto getById(Integer id);

    Aeropuerto getCodigoATA(AeropuertoDTO aeropuertoDTO);

    Aeropuerto getNombreAeropuerto(AeropuertoDTO aeropuertoDTO);

    List<Aeropuerto> getCity(AeropuertoDTO aeropuertoDTO);

    List<Aeropuerto> getCountry(AeropuertoDTO aeropuertoDTO);

    List<Aeropuerto> getAeropuestos();

    Aeropuerto updateAeropuerto(AeropuertoDTO aeropuertoDTO);

    <T> DeleteResponse<T> deleteById(AeropuertoDTO aeropuertoDTO);
}
