package com.ms_reserve.reserve.controller;

import com.ms_reserve.reserve.model.Reserve;
import com.ms_reserve.reserve.service.ReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReserveController {

    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping
    public ResponseEntity<Reserve> createReserve(@RequestBody Reserve reserve) {
        return ResponseEntity.ok(reserveService.createReserve(reserve));
    }

    @GetMapping
    public ResponseEntity<List<Reserve>> getAllReserves() {
        return ResponseEntity.ok(reserveService.getAllReserves());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(@PathVariable Long id) {
        return ResponseEntity.ok(reserveService.getReserveById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserve> updateReserve(@PathVariable Long id, @RequestBody Reserve reserve) {
        return ResponseEntity.ok(reserveService.updateReserve(id, reserve));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable Long id) {
        reserveService.deleteReserve(id);
        return ResponseEntity.noContent().build();
    }
}
