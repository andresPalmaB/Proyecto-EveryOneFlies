package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    List<Reserve> findReserveByFlight(Flight flight);

    Boolean existsReserveBySeat(Seat seat);

    List<Reserve> findReserveByReserveCode(String reserveCode);
}
