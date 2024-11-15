package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.dto.dtoModel.PassengerDTO;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.repository.PassengerRepository;
import com.betek.everyOneFlies.service.serviceInterface.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository repository;

    @Override
    public Passenger createPassenger(PassengerDTO passengerDTO, Reserve reserve) {
        return repository.save(
                new Passenger(
                        reserve,
                        passengerDTO.isResponsibleForPayment(),
                        passengerDTO.firstName(),
                        passengerDTO.lastName(),
                        passengerDTO.email(),
                        passengerDTO.phone()
                )
        );
    }

    @Override
    public Passenger getPassengerById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Passenger with ID " + id + " not found."
                )
        );
    }

    @Override
    public Passenger getPassengerByReserve(Reserve reserve){
        return repository.findPassengerByReserve(reserve).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Passenger with reserve " + reserve + " not found.")
        );
    }

    @Override
    public Passenger updatePassenger(Passenger passenger) {

        Passenger found = this.getPassengerById(passenger.getIdPassenger());

        found.setFirstName(passenger.getFirstName());
        found.setLastName(passenger.getLastName());
        found.setEmail(passenger.getEmail());
        found.setPhone(passenger.getPhone());

        return repository.save(passenger);
    }

    @Override
    public void deletePassenger(Reserve reserve) {

        repository.deletePassengerByReserve(reserve);

    }
}
