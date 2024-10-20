package com.ms_reserve.reserve.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightId;
    private LocalDate dateReserve;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Passenger> passengers;
}
