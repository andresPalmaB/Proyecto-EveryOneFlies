package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.model.modelEnum.TipoAsiento;
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
@Entity
@Table(name = "reservation")
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "ID_VUELO", nullable = false)
    private Long flightId;  // Referencia al vuelo por su ID

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reserve_id")
    private List<Passenger> passengers;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TipoAsiento category;

    public Reserve(Long flightId, List<Passenger> passengers, LocalDate reservationDate, ReservationStatus status, TipoAsiento category) {
        this.flightId = flightId;
        this.passengers = passengers;
        this.reservationDate = reservationDate;
        this.status = status;
        this.category = category;
    }

    public void updateStatus(ReservationStatus newStatus) {
        this.status = newStatus;
    }
}