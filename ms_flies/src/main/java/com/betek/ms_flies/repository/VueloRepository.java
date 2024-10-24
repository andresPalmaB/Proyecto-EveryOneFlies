package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Aeropuerto;
import com.betek.ms_flies.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Optional<Vuelo> findByCodigo(String codigo);

    List<Vuelo> findByFechaSalidaAndOrigenAndDestino(LocalDate departureDate,
                                                     Aeropuerto origen,
                                                     Aeropuerto destino);

    List<Vuelo> findByFechaSalidaBetweenAndOrigenAndDestino(LocalDate startOfDay,
                                                            LocalDate endOfDay,
                                                            Aeropuerto origen,
                                                            Aeropuerto destino);
}
