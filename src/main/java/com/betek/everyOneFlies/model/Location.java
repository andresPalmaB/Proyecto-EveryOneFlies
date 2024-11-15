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
    @Size(min = 4, max = 30, message = "Country must be between 4 and 30 characters")
    private String country;

    @Column(name = "CITY", nullable = false, length = 15)
    @NotBlank(message = "City is mandatory")
    @Size(min = 3, max = 30, message = "City must be between 3 and 30 characters")
    private String city;

    @Column(name = "FECHA_INICIO_ALTA", nullable = false)
    @NotNull(message = "High Season start date 1 is mandatory")
    private LocalDate startDateH;

    @Column(name = "FECHA_FIN_ALTA", nullable = false)
    @NotNull(message = "High Season end date 1 is mandatory")
    private LocalDate endDateH;

    @Column(name = "FECHA_INICIO_ALTA_DOS", nullable = false)
    @NotNull(message = "High Season start date 2 is mandatory")
    private LocalDate startDateH2;

    @Column(name = "FECHA_FIN_ALTA_DOS", nullable = false)
    @NotNull(message = "High Season end date 2 is mandatory")
    private LocalDate endDateH2;

    @Column(name = "FECHA_INICIO_MEDIA", nullable = false)
    @NotNull(message = "Mid Season start date 1 is mandatory")
    private LocalDate startDateM;

    @Column(name = "FECHA_FIN_MEDIA", nullable = false)
    @NotNull(message = "Mid Season end date 1 is mandatory")
    private LocalDate endDateM;

    @Column(name = "FECHA_INICIO_MEDIA_DOS", nullable = false)
    @NotNull(message = "Mid Season start date 2 is mandatory")
    private LocalDate startDateM2;

    @Column(name = "FECHA_FIN_MEDIA_DOS", nullable = false)
    @NotNull(message = "Mid Season end date 2 is mandatory")
    private LocalDate endDateM2;

    @Column(name = "BASE_PRICE", nullable = false)
    @NotNull(message = "Base Price is mandatory")
    private Double basePrice;

    public Location(LocationDTO locationDTO) {
        this.country = locationDTO.country().toLowerCase();
        this.city = locationDTO.city().toLowerCase();
        this.startDateH = locationDTO.startDateH();
        this.endDateH = locationDTO.endDateH();
        this.startDateH2 = locationDTO.startDateH2();
        this.endDateH2 = locationDTO.endDateH2();
        this.startDateM = locationDTO.startDateM();
        this.endDateM = locationDTO.endDateM();
        this.startDateM2 = locationDTO.startDateM2();
        this.endDateM2 = locationDTO.endDateM2();
        this.basePrice = locationDTO.basePrice();
    }
}
