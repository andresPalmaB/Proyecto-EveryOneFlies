package com.betek.ms_flies.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AEROPUERTO")
@SequenceGenerator(name = "aeropueto_seq", sequenceName = "aeropuerto_sequence", allocationSize = 1)
public class Aeropuerto {

    @Id
    @Column(name = "ID_AEROPUERTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aeropueto_seq")
    private int idAeropuerto;

    @Column(name = "CODIGO_IATA")
    private String codigoIATA;

    @Column(name = "NOMBRE_AEROPUERTO")
    private String nombreAeropuerto;

    @OneToOne
    @JoinColumn(name = "ID_LOCATION", referencedColumnName = "ID_LOCATION")
    private Location location;

    public Aeropuerto(String codigoATA, String nombreAeropuerto, Location location) {
        this.codigoIATA = codigoATA;
        this.nombreAeropuerto = nombreAeropuerto;
        this.location = location;
    }
}
