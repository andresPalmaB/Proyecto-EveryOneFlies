package com.betek.ms_flies.model;

import com.betek.ms_flies.dto.dtoModel.LocationDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @Column(name = "ID_LOCATION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLocation;

    @Column(name = "PAIS", nullable = false, length = 15)
    private String country;

    @Column(name = "CIUDAD", nullable = false, length = 15)
    private String city;

    @Column(name = "FECHA_INICIO_ALTA", nullable = false)
    private LocalDate highSeasonStartDate;

    @Column(name = "FECHA_FIN_ALTA", nullable = false)
    private LocalDate highSeasonEndDate;

    @Column(name = "FECHA_INICIO_ALTA_DOS", nullable = false)
    private LocalDate highSeasonStartDate2;

    @Column(name = "FECHA_FIN_ALTA_DOS", nullable = false)
    private LocalDate highSeasonEndDate2;

    @Column(name = "FECHA_INICIO_MEDIA", nullable = false)
    private LocalDate midSeasonStartDate;

    @Column(name = "FECHA_FIN_MEDIA", nullable = false)
    private LocalDate midSeasonEndDate;

    @Column(name = "FECHA_INICIO_MEDIA_DOS", nullable = false)
    private LocalDate midSeasonStartDate2;

    @Column(name = "FECHA_FIN_MEDIA_DOS", nullable = false)
    private LocalDate midSeasonEndDate2;

    @Column(name = "PRECIO_BASE", nullable = false)
    private double basePrice;

    public Location(LocationDTO locationDTO) {
        this.country = locationDTO.country().toLowerCase();
        this.city = locationDTO.city().toLowerCase();
        this.highSeasonStartDate = locationDTO.fechaInicioAlta();
        this.highSeasonEndDate = locationDTO.fechaFinAlta();
        this.highSeasonStartDate2 = locationDTO.fechaInicioAlta2();
        this.highSeasonEndDate2 = locationDTO.fechaFinAlta2();
        this.midSeasonStartDate = locationDTO.fechaInicioMedia();
        this.midSeasonEndDate = locationDTO.fechaFinMedia();
        this.midSeasonStartDate2 = locationDTO.fechaInicioMedia2();
        this.midSeasonEndDate2 = locationDTO.fechaFinMedia2();
        this.basePrice = locationDTO.precioBase();
    }
}
