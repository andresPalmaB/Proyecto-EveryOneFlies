package com.betek.ms_flies.repository;

import com.betek.ms_flies.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findLocationByCity(String city);

    List<Location> findLocationByCountry(String country);

}
