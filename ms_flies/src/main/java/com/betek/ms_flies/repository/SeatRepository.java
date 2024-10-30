package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Flight;
import com.betek.ms_flies.model.Seat;
import com.betek.ms_flies.model.modelEnum.TipoAsiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    Optional<Seat> findSeatBySeatCode(String seatCode);

    List<Seat> findSeatByFlightAndTipoAsientoAndAvailable(
            Flight flight,
            TipoAsiento tipoAsiento,
            Boolean available
    );

    List<Seat> findSeatByFlightAndAvailable(Flight flight, Boolean available);

    long countSeatByFlight(Flight flight);

}
