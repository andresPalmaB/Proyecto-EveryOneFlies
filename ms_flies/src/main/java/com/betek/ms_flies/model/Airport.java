package com.betek.ms_flies.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "AEROPUERTO")
public class Airport {

    @Id
    @Column(name = "ID_AEROPUERTO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAirport;

    @Column(name = "CODIGO_IATA", nullable = false, length = 3)
    private String iataCode;

    @Column(name = "NOMBRE_AEROPUERTO", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "ID_LOCATION", referencedColumnName = "ID_LOCATION", nullable = false)
    private Location location;

    public Airport(String iataCode, String name, Location location) {
        this.iataCode = iataCode;
        this.name = name;
        this.location = location;
    }
}
