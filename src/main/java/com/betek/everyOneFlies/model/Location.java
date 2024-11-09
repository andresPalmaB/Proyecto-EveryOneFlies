package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.dto.dtoModel.LocationDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "LOCATIONS")
public class Location {

    @Id
    @Column(name = "ID_LOCATION")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLocation;

    @Column(name = "COUNTRY", nullable = false, length = 15)
    @NotBlank(message = "Country is mandatory")
    @Size(min = 4, max = 15, message = "Country must be between 4 and 15 characters")
    private String country;

    @Column(name = "CITY", nullable = false, length = 15)
    @NotBlank(message = "City is mandatory")
    @Size(min = 3, max = 15, message = "City must be between 3 and 15 characters")
    private String city;

    @Column(name = "FECHA_INICIO_ALTA", nullable = false)
    @NotNull(message = "High Season start date 1 is mandatory")
    private LocalDate highSeasonStartDate;

    @Column(name = "FECHA_FIN_ALTA", nullable = false)
    @NotNull(message = "High Season end date 1 is mandatory")
    private LocalDate highSeasonEndDate;

    @Column(name = "FECHA_INICIO_ALTA_DOS", nullable = false)
    @NotNull(message = "High Season start date 2 is mandatory")
    private LocalDate highSeasonStartDate2;

    @Column(name = "FECHA_FIN_ALTA_DOS", nullable = false)
    @NotNull(message = "High Season end date 2 is mandatory")
    private LocalDate highSeasonEndDate2;

    @Column(name = "FECHA_INICIO_MEDIA", nullable = false)
    @NotNull(message = "Mid Season start date 1 is mandatory")
    private LocalDate midSeasonStartDate;

    @Column(name = "FECHA_FIN_MEDIA", nullable = false)
    @NotNull(message = "Mid Season end date 1 is mandatory")
    private LocalDate midSeasonEndDate;

    @Column(name = "FECHA_INICIO_MEDIA_DOS", nullable = false)
    @NotNull(message = "Mid Season start date 2 is mandatory")
    private LocalDate midSeasonStartDate2;

    @Column(name = "FECHA_FIN_MEDIA_DOS", nullable = false)
    @NotNull(message = "Mid Season end date 2 is mandatory")
    private LocalDate midSeasonEndDate2;

    @Column(name = "BASE_PRICE", nullable = false)
    @NotNull(message = "Base Price is mandatory")
    private Double basePrice;

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
