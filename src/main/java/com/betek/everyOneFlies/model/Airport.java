package com.betek.everyOneFlies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "AIRPORTS")
public class Airport {

    @Id
    @Column(name = "ID_AIRPORT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAirport;

    @NotBlank(message = "Airport IATA code is mandatory")
    @Column(name = "IATA_CODE", nullable = false, length = 3)
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    @Column(name = "AIRPORT_NAME", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "ID_LOCATION", referencedColumnName = "ID_LOCATION", nullable = false)
    @NotNull(message = "Location is mandatory")
    private Location location;

    public Airport(String iataCode, String name, Location location) {
        this.iataCode = iataCode;
        this.name = name;
        this.location = location;
    }
}
