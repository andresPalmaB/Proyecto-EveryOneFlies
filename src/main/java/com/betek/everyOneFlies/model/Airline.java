package com.betek.everyOneFlies.model;

import com.betek.everyOneFlies.dto.dtoModel.AirlineDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 15, message = "Name should not exceed 15 characters")

    private String name;

    @NotBlank(message = "Airline acronym is mandatory")
    @Size(min = 1, max = 3, message = "Name should not exceed 3 characters")
    @Column(name = "ACRONYM", nullable = false, length = 3)
    private String acronym;

    public Airline(String name, String acronym) {
        this.name = name;
        this.acronym = acronym;
    }
}
