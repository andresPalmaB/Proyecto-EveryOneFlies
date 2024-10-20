package com.ms_reserve.reserve.service;

import com.ms_reserve.reserve.exceptions.ResourceNotFoundException;
import com.ms_reserve.reserve.model.Reserve;
import com.ms_reserve.reserve.repository.ReserveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;

    public ReserveService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    public Reserve createReserve(Reserve reserve) {
        // valid fligh, seats and exceptions / optional
        return reserveRepository.save(reserve);
    }

    public List<Reserve> getAllReserves() {
        return reserveRepository.findAll();
    }

    public Reserve getReserveById(Long id) {
        return reserveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + id));
    }

    public Reserve updateReserve(Long id, Reserve updatedReserve) {
        Reserve reserve = getReserveById(id);
        reserve.setStatus(updatedReserve.getStatus());
        reserve.setPassengers(updatedReserve.getPassengers());
        reserve.setStatus(updatedReserve.getStatus());
        return reserveRepository.save(reserve);
    }

    public void deleteReserve(Long id) {
        Reserve reserve = getReserveById(id);
        reserveRepository.delete(reserve);
    }
}
