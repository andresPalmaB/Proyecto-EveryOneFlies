package com.betek.everyOneFlies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "FLIGHTS")
public class Flight {

    @Id
    @Column(name = "ID_FLIGHT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFlight;

    @NotBlank(message = "Flight code is mandatory")
    @Column(name = "FLIGHT_CODE")
    private String flightCode;

    @ManyToOne
    @JoinColumn(name = "ID_AIRLINE", referencedColumnName = "ID_AIRLINE", nullable = false)
    @NotNull(message = "Airline is mandatory")
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "ID_ORIGIN_AIRPORT", referencedColumnName = "ID_AIRPORT", nullable = false)
    @NotNull(message = "Origin Airport is mandatory")
    private Airport origin;

    @ManyToOne
    @JoinColumn(name = "ID_DESTINATION_AIRPORT", referencedColumnName = "ID_AIRPORT", nullable = false)
    @NotNull(message = "Destination Airport is mandatory")
    private Airport destination;

    @Column(name = "DEPARTURE_DATE", nullable = false)
    @NotNull(message = "Departure date is mandatory")
    private LocalDate departureDate;

    @Column(name = "DEPARTURE_TIME", nullable = false)
    @NotNull(message = "Departure time is mandatory")
    private LocalTime departureTime;

    @Column(name = "ARRIVAL_DATE", nullable = false)
    @NotNull(message = "Arrival date is mandatory")
    private LocalDate arrivalDate;

    @Column(name = "ARRIVAL_TIME", nullable = false)
    @NotNull(message = "Arrival time is mandatory")
    private LocalTime arrivalTime;

    @Column(name = "ECONOMY", nullable = false)
    @NotNull(message = "Economy counter is mandatory")
    private Integer economyCounter;

    @Column(name = "PREMIUM_ECONOMY", nullable = false)
    @NotNull(message = "Premium Economy counter is mandatory")
    private Integer premiumEconomyCounter;

    @Column(name = "BUSINESS", nullable = false)
    @NotNull(message = "Business counter is mandatory")
    private Integer businessCounter;

    @Column(name = "FIRST_CLASS", nullable = false)
    @NotNull(message = "First Class counter is mandatory")
    private Integer firstClassCounter;

    @Column(name = "TOTAL_SEATS", nullable = false)
    @NotNull(message = "Total Seats is mandatory")
    private Integer totalSeats;

    public Flight(Airline airline,
                  Airport origin, Airport destination, LocalDate departureDate,
                  LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime,
                  Integer economyCounter, Integer premiumEconomyCounter, Integer businessCounter,
                  Integer firstClassCounter) {

        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.economyCounter = economyCounter;
        this.premiumEconomyCounter = premiumEconomyCounter;
        this.businessCounter = businessCounter;
        this.firstClassCounter = firstClassCounter;
        this.totalSeats = economyCounter + premiumEconomyCounter + businessCounter + firstClassCounter;
    }

    @PostPersist
    public void initializeCode(){
        this.flightCode = this.airline.getAcronym().toLowerCase() + this.idFlight.intValue();
    }
}
