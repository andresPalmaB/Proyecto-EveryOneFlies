package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.SeatCategory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "SEATS")
public class Seat {

    @Id
    @Column(name = "ID_SEAT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSeat;

    @Column(name = "SEAT_CODE")
    private String seatCode;

    @ManyToOne
    @JoinColumn(name = "ID_FLIGHT", referencedColumnName = "ID_FLIGHT", nullable = false)
    private Flight flight;

    @Column(name = "AVAILABLE", nullable = false)
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEAT_CATEGORY", nullable = false)
    private SeatCategory seatCategory;

    public Seat(Flight flight, Boolean available, SeatCategory seatCategory, int seatNumbre) {
        this.flight = flight;
        this.available = available;
        this.seatCategory = seatCategory;
        this.seatCode = seatNumbre + flight.getFlightCode();
    }

    @Override
    public String toString() {
        return  "Code: " + seatCode + '\n' +
                "Seat Category:" + seatCategory;
    }
}
