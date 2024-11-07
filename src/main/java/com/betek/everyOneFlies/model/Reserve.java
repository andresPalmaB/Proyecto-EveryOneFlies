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
    @Column(name = "ID_RESERVACION")
    private Long idReserve;

    @Column(name = "CODIGO")
    private String reserveCode;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "ID_VUELO", referencedColumnName = "ID_VUELO", nullable = false)
    private Flight flight;

    @OneToOne
    @JoinColumn(name = "ID_ASIENTO", referencedColumnName = "ID_ASIENTO", nullable = false)
    private Seat seat;

    public Reserve(LocalDate reservationDate,
                   Flight flight, Seat seat) {
        this.reservationDate = reservationDate;
        this.status = ReservationStatus.PENDING;
        this.flight = flight;
        this.seat = seat;
    }

    @PostPersist
    public void inicializarCodigo(){
        this.reserveCode = getFlight().getFlightCode() + "-R" + this.getIdReserve();
    }

    public void updateStatus(ReservationStatus newStatus) {
        this.status = newStatus;
    }
}