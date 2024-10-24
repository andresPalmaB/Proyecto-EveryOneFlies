package com.betek.ms_flies.service;

import com.betek.ms_flies.dto.DeleteResponse;
import com.betek.ms_flies.dto.RutaIda;
import com.betek.ms_flies.dto.RutaIdaVuelta;
import com.betek.ms_flies.dto.dtoModel.VueloAsientoVendidoDTO;
import com.betek.ms_flies.dto.dtoModel.VueloDTO;
import com.betek.ms_flies.exception.ResourceNotFoundException;
import com.betek.ms_flies.model.Aerolinea;
import com.betek.ms_flies.model.Aeropuerto;
import com.betek.ms_flies.model.Vuelo;
import com.betek.ms_flies.repository.VueloRepository;
import com.betek.ms_flies.service.serviceInterface.AerolineService;
import com.betek.ms_flies.service.serviceInterface.AeropuertoService;
import com.betek.ms_flies.service.serviceInterface.AsientoService;
import com.betek.ms_flies.service.serviceInterface.VueloService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class VueloServiceImpl implements VueloService {

    @Autowired
    private VueloRepository repository;

    @Autowired
    private AerolineService aerolineaService;

    @Autowired
    private AeropuertoService aeropuertoService;

    @Autowired
    private AsientoService asientoService;

    @Override
    public Vuelo createVuelo(VueloDTO vueloDTO) {

        Aerolinea aerolinea = aerolineaService.getById(vueloDTO.idAerolinea());

        Aeropuerto aeropuertoOrigen = aeropuertoService.getById(vueloDTO.idAeropuertoOrigen());

        Aeropuerto aeropuertoDestino = aeropuertoService.getById(vueloDTO.idAeropuertoDestino());

        return repository.save(
                new Vuelo(
                        aerolinea,
                        aeropuertoOrigen,
                        aeropuertoDestino,
                        vueloDTO.fechaSalida(),
                        vueloDTO.horaSalida(),
                        vueloDTO.fechaLlegada(),
                        vueloDTO.horaLlegada(),
                        vueloDTO.contadorEconomicos(),
                        vueloDTO.contadorEconomicosPremiun(),
                        vueloDTO.contadorBusiness(),
                        vueloDTO.contadorPrimeraClase()
                )
        );
    }

    @Override
    public Vuelo getVueloByCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Vuelo con codigo " + codigo + " no encontrada."));
    }

    @Override
    public List<Vuelo> getRutaByFecha(RutaIda ruta) {
        return repository.findByFechaSalidaAndOrigenAndDestino(
                ruta.departureDate(),
                aeropuertoService.getCity(ruta.aeropuertoOrigenDTO()).stream().findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Ciudad " + ruta.aeropuertoOrigenDTO() + " no encontrada.")),
                aeropuertoService.getCity(ruta.aeropuertoDestinoDTO()).stream().findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Ciudad " + ruta.aeropuertoDestinoDTO() + " no encontrada."))
        );
    }

    @Override
    public List<Vuelo> getRutaByRangoFechas(RutaIdaVuelta ruta) {
        return repository.findByFechaSalidaBetweenAndOrigenAndDestino(
                ruta.departureDate(),
                ruta.returnDate(),
                aeropuertoService.getCity(ruta.aeropuertoOrigenDTO()).stream().findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Ciudad " + ruta.aeropuertoOrigenDTO() + " no encontrada.")),
                aeropuertoService.getCity(ruta.aeropuertoDestinoDTO()).stream().findFirst()
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Ciudad " + ruta.aeropuertoDestinoDTO() + " no encontrada."))
        );
    }

    @Override
    @Transactional
    public Vuelo updateFechaVuelo(Vuelo vuelo) {
        Vuelo found = repository.findById(vuelo.getIdVuelo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        vuelo.getClass().getSimpleName() + " con ID " +
                                vuelo.getIdVuelo() + " no encontrado"));

        found.setFechaSalida(vuelo.getFechaSalida());
        found.setHoraSalida(vuelo.getHoraSalida());
        found.setFechaLlegada(vuelo.getFechaLlegada());
        found.setHoraLlegada(vuelo.getHoraLlegada());

        return repository.save(found);
    }

    @Override
    public Vuelo updateAsientoVendidosVuelo(VueloAsientoVendidoDTO asientoVendidoDTO) {

        Vuelo found = this.getVueloByCodigo(asientoVendidoDTO.codigo());

        found.setContadorEconomicos(
                found.getContadorEconomicos() - asientoVendidoDTO.asientoVendidosEconomicos()
        );

        found.setContadorEconomicosPremiun(
                found.getContadorEconomicosPremiun() - asientoVendidoDTO.asientoVendidosEconomicosPremiun()
        );

        found.setContadorBusiness(
                found.getContadorBusiness() - asientoVendidoDTO.asientoVendidosBusiness()
        );

        found.setContadorPrimeraClase(
                found.getContadorPrimeraClase() - asientoVendidoDTO.asientoVendidosPrimeraClase()
        );

        return repository.save(found);
    }

    @Override
    public <T> DeleteResponse<T> deleteById(Vuelo vuelo) {
        Vuelo object = repository.findById(vuelo.getIdVuelo())
                .orElseThrow(() -> new ResourceNotFoundException(
                        vuelo.getClass().getSimpleName() + " con ID " +
                                vuelo.getIdVuelo() + " no encontrado"));

        repository.delete(vuelo);

        return new DeleteResponse<>(object.getClass().getSimpleName(), vuelo.getCodigo());
    }
}
