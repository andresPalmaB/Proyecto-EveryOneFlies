package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "reservation")
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVACION")
    private Long idReserve;

    @NotBlank(message = "Reservation code is mandatory")
    @Column(name = "RESERVATION_CODE")
    private String reserveCode;

    @Column(name = "RESERVATION_DATE", nullable = false)
    @NotNull(message = "Reservation date is mandatory")
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "RESERVATION_STATUS", nullable = false)
    @NotNull(message = "Reservation status is mandatory")
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "ID_FLIGHT", referencedColumnName = "ID_FLIGHT", nullable = false)
    @NotNull(message = "Flight is mandatory")
    private Flight flight;

    @OneToOne
    @JoinColumn(name = "ID_SEAT", referencedColumnName = "ID_SEAT", nullable = false)
    @NotNull(message = "Seat is mandatory")
    private Seat seat;

    public Reserve(LocalDate reservationDate,
                   Flight flight, Seat seat, String reserveCode) {
        this.reservationDate = reservationDate;
        this.status = ReservationStatus.PENDING;
        this.flight = flight;
        this.seat = seat;
        this.reserveCode = reserveCode;
    }

    public void updateStatus(ReservationStatus newStatus) {
        this.status = newStatus;
    }
}