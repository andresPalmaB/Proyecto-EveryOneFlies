package com.betek.everyOneFlies.service.serviceInterface;

import com.betek.everyOneFlies.dto.dtoModel.PassengerDTO;
import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Reserve;

import java.util.List;

public interface PassengerService {

    Passenger createPassenger(PassengerDTO passengerDTO, Reserve reserve);

    Passenger getPassengerById(Long id);

    Passenger getPassengerByReserve(Reserve reserve);

    Passenger updatePassenger(Passenger passenger);

    void deletePassenger(Reserve reserve);

}
