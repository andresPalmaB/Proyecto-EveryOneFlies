package com.betek.ms_flies.model;

import jakarta.persistence.*;
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
@Table(name = "VUELO")
public class Flight {

    @Id
    @Column(name = "ID_VUELO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFlight;

    @Column(name = "CODIGO")
    private String flightCode;

    @ManyToOne
    @JoinColumn(name = "ID_AEROLINEA", referencedColumnName = "ID_AEROLINEA", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "ID_AEROPUERTO_ORIGEN", referencedColumnName = "ID_AEROPUERTO", nullable = false)
    private Airport origin;

    @ManyToOne
    @JoinColumn(name = "ID_AEROPUERTO_DESTINO", referencedColumnName = "ID_AEROPUERTO", nullable = false)
    private Airport destination;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate departureDate;

    @Column(name = "HORA_SALIDA", nullable = false)
    private LocalTime departureTime;

    @Column(name = "FECHA_LLEGADA", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "HORA_LLEGADA", nullable = false)
    private LocalTime arrivalTime;

    @Column(name = "ECONOMICOS", nullable = false)
    private int economyCounter;

    @Column(name = "ECONOMICOS_PREMIUN", nullable = false)
    private int premiumEconomyCounter;

    @Column(name = "BUSINESS", nullable = false)
    private int businessCounter;

    @Column(name = "PRIMERA_CLASE", nullable = false)
    private int firstClassCounter;

    @Column(name = "TOTAL_PUESTOS", nullable = false)
    private int totalSeats;

    public Flight(Airline aerolinea,
                  Airport origen, Airport destino, LocalDate fechaSalida,
                  LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada,
                  int contadorEconomicos, int contadorEconomicosPremiun, int contadorBusiness,
                  int contadorPrimeraClase) {

        this.airline = aerolinea;
        this.origin = origen;
        this.destination = destino;
        this.departureDate = fechaSalida;
        this.departureTime = horaSalida;
        this.arrivalDate = fechaLlegada;
        this.arrivalTime = horaLlegada;
        this.economyCounter = contadorEconomicos;
        this.premiumEconomyCounter = contadorEconomicosPremiun;
        this.businessCounter = contadorBusiness;
        this.firstClassCounter = contadorPrimeraClase;
        this.totalSeats = contadorEconomicos + contadorEconomicosPremiun + contadorBusiness + contadorPrimeraClase;
    }

    @PostPersist
    public void inicializarCodigo(){
        this.flightCode = this.airline.getAcronym().toLowerCase() + this.idFlight.intValue();
    }
}
