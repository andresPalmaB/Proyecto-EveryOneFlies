package com.betek.ms_flies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "AEROLINEA")
@SequenceGenerator(name = "aerolinea_seq", sequenceName = "aerolinea_sequence", allocationSize = 1)
public class Aerolinea {

    @Id
    @Column(name = "ID_AEROLINEA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aerolinea_seq")
    private int idAerolinea;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "SIGLAS")
    private String siglas;

    public Aerolinea(String nombre, String siglas) {
        this.nombre = nombre;
        this.siglas = siglas;
    }
}
