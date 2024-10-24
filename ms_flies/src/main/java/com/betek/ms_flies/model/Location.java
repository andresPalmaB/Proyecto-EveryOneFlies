package com.betek.ms_flies.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LOCATION")
@SequenceGenerator(name = "location_seq", sequenceName = "location_sequence", allocationSize = 1)
public class Location {

    @Id
    @Column(name = "ID_LOCATION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    private int idLocation;

    @Column(name = "PAIS")
    private String country;

    @Column(name = "CIUDAD")
    private String city;

    @Column(name = "FECHA_INICIO_ALTA")
    private LocalDate fechaInicioAlta;

    @Column(name = "FECHA_FIN_ALTA")
    private LocalDate fechaFinAlta;

    @Column(name = "FECHA_INICIO_MEDIA")
    private LocalDate fechaInicioMedia;

    @Column(name = "FECHA_FIN_MEDIA")
    private LocalDate fechaFinMedia;

    @Column(name = "FECHA_INICIO_MEDIA_DOS")
    private LocalDate fechaInicioMedia2;

    @Column(name = "FECHA_FIN_MEDIA_DOS")
    private LocalDate fechaFinMedia2;

    @Column(name = "PRECIO_BASE")
    private double precioBase;

    public Location(String country, String city, LocalDate fechaInicioAlta,
                    LocalDate fechaFinAlta, LocalDate fechaInicioMedia,
                    LocalDate fechaFinMedia, LocalDate fechaInicioMedia2,
                    LocalDate fechaFinMedia2, double precioBase) {

        this.country = country;
        this.city = city;
        this.fechaInicioAlta = fechaInicioAlta;
        this.fechaFinAlta = fechaFinAlta;
        this.fechaInicioMedia = fechaInicioMedia;
        this.fechaFinMedia = fechaFinMedia;
        this.fechaInicioMedia2 = fechaInicioMedia2;
        this.fechaFinMedia2 = fechaFinMedia2;
        this.precioBase = precioBase;

    }
}
