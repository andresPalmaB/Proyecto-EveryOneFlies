package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Aeropuerto;
import com.betek.ms_flies.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AeropuestoRepository extends JpaRepository<Aeropuerto, Integer> {

    Optional<Aeropuerto> findByCodigoIATA(String codigo);

    Optional<Aeropuerto> findByNombreAeropuerto(String nombre);

    List<Aeropuerto> findByLocation(Location location);

    List<Aeropuerto> findByLocationIn(List<Location> location);

}
