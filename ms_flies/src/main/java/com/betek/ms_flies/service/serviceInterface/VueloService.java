package com.betek.ms_flies.service.serviceInterface;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.RutaIda;
import com.betek.ms_flies.dto.RutaIdaVuelta;
import com.betek.ms_flies.dto.dtoModel.VueloAsientoVendidoDTO;
import com.betek.ms_flies.dto.dtoModel.VueloDTO;
import com.betek.ms_flies.model.Vuelo;

import java.util.List;

public interface VueloService {

    Vuelo createVuelo(VueloDTO vueloDTO);

    Vuelo getVueloByCodigo(String codigo);

    List<Vuelo> getRutaByFecha(RutaIda ruta);

    List<Vuelo> getRutaByRangoFechas(RutaIdaVuelta ruta);

    Vuelo updateFechaVuelo(Vuelo vuelo);

    Vuelo updateAsientoVendidosVuelo(VueloAsientoVendidoDTO asientoVendidoDTO);

    <T> DeleteResponse<T> deleteById(Vuelo vuelo);

}
