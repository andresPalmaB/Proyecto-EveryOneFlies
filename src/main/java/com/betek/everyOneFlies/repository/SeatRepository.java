package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Seat;
import com.betek.everyOneFlies.model.modelEnum.TipoAsiento;
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

    void deleteAllByFlight(Flight flight);
}
