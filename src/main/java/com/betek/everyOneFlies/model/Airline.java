package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "AEROLINEA")
public class Airline {

    @Id
    @Column(name = "ID_AEROLINEA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAirline;

    @Column(name = "NOMBRE", nullable = false, length = 15)
    private String name;

    @Column(name = "SIGLAS", nullable = false, length = 3)
    private String acronym;

    public Airline(AirlineDTO airlineDTO) {
        this.name = airlineDTO.name().toLowerCase();
        this.acronym = airlineDTO.acronym().toLowerCase();
    }
}
