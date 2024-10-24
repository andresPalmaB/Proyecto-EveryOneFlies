package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Asiento;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer> {

    Optional<Asiento> findByAsientoVuelo(Integer asientoVuelo);

    List<Asiento> findByAsientoVueloAndTipoAsientoAndDisponible(
            Integer asientoVuelo,
            TipoAsiento tipoAsiento,
            Boolean disponibilidad);

    List<Asiento> findByAsientoVueloAndDisponible(Integer asientoVuelo, Boolean disponible);

    List<Asiento> findAll();
}
