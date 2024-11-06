package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.model.modelEnum.TipoAsiento;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ASIENTOS")
public class Seat {

    @Id
    @Column(name = "ID_ASIENTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSeat;

    @Column(name = "CODIGO_ASIENTO")
    private String seatCode;

    @ManyToOne
    @JoinColumn(name = "ID_VUELO", referencedColumnName = "ID_VUELO", nullable = false)
    private Flight flight;

    @Column(name = "DISPONIBLE", nullable = false)
    private boolean available;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ASIENTO", nullable = false)
    private TipoAsiento tipoAsiento;

    public Seat(Flight flight, boolean disponible, TipoAsiento tipoAsiento, int seatNumbre) {
        this.flight = flight;
        this.available = disponible;
        this.tipoAsiento = tipoAsiento;
        this.seatCode = seatNumbre + flight.getFlightCode();
    }

}
