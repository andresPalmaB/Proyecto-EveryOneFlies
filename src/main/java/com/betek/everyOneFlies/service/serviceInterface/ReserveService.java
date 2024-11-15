package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.ReserveDTO;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveService {

    List<Reserve> createReserve(ReserveDTO reserveDTO);

    List<Reserve> getReservesFromFlight(String flightCode);

    Reserve getReserveById(Long id);

    List<Reserve> getReservesByReserveCode(String reserveCode);

    List<Reserve> updateReserveStatus(String reserveCode, String newStatus, LocalDateTime now);

}
