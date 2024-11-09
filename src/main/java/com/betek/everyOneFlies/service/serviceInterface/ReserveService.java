package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.DeleteResponse;
import com.betek.everyOneFlies.dto.dtoModel.ReserveDTO;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;

import java.util.List;

public interface ReserveService {

    List<Reserve> createReserve(ReserveDTO reserveDTO);

    List<Reserve> getReservesFromFlight(String flightCode);

    Reserve getReserveById(Long id);

    List<Reserve> getReservesByReserveCode(String reserveCode);

    List<Reserve> updateReserveStatus(String reserveCode, ReservationStatus newStatus);

    <T> DeleteResponse<T> deleteReserve(Reserve reserve);

    void deleteReserves(String reserveCode);
}
