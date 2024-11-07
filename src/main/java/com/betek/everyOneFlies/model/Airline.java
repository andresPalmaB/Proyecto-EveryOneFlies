package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "AIRLINES")
public class Airline {

    @Id
    @Column(name = "ID_AIRLINE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAirline;

    @NotBlank(message = "Airline name is mandatory")
    @Column(name = "NAME", nullable = false, length = 15)
    private String name;

    @NotBlank(message = "Airline acronym is mandatory")
    @Column(name = "ACRONYM", nullable = false, length = 3)
    private String acronym;

    public Airline(AirlineDTO airlineDTO) {
        this.name = airlineDTO.name().toLowerCase();
        this.acronym = airlineDTO.acronym().toLowerCase();
    }
}
