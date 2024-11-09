package com.betek.everyOneFlies.repository;

import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findPassengerByReserve(Reserve reserve);

    void deleteAllByReserve(Reserve reserve);

}
