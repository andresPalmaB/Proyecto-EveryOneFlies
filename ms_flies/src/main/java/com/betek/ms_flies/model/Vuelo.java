package com.betek.ms_flies.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VUELO")
@SequenceGenerator(name = "vuelo_seq", sequenceName = "vuelo_sequence", allocationSize = 50)
public class Vuelo {

    @Id
    @Column(name = "ID_VUELO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vueloseq")
    private Long idVuelo;

    @Column(name = "CODIGO")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "ID_AEROLINEA", referencedColumnName = "ID_AEROLINEA")
    private Aerolinea aerolinea;

    @OneToOne
    @JoinColumn(name = "ID_AEROPUERTO_ORIGEN", referencedColumnName = "ID_AEROPUERTO")
    private Aeropuerto origen;

    @OneToOne
    @JoinColumn(name = "ID_AEROPUERTO_DESTINO", referencedColumnName = "ID_AEROPUERTO")
    private Aeropuerto destino;

    @Column(name = "FECHA_SALIDA")
    private LocalDate fechaSalida;

    @Column(name = "HORA_SALIDA")
    private LocalTime horaSalida;

    @Column(name = "FECHA_LLEGADA")
    private LocalDate fechaLlegada;

    @Column(name = "HORA_LLEGADA")
    private LocalTime horaLlegada;

    @Column(name = "ECONOMICOS")
    private int contadorEconomicos;

    @Column(name = "ECONOMICOS_PREMIUN")
    private int contadorEconomicosPremiun;

    @Column(name = "BUSINESS")
    private int contadorBusiness;

    @Column(name = "PRIMERA_CLASE")
    private int contadorPrimeraClase;

    @Column(name = "TOTAL_PUESTOS")
    private int totalPuestos;

    public Vuelo(Aerolinea aerolinea,
                 Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida,
                 LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada,
                 int contadorEconomicos, int contadorEconomicosPremiun, int contadorBusiness,
                 int contadorPrimeraClase) {

        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.contadorEconomicos = contadorEconomicos;
        this.contadorEconomicosPremiun = contadorEconomicosPremiun;
        this.contadorBusiness = contadorBusiness;
        this.contadorPrimeraClase = contadorPrimeraClase;
        this.totalPuestos = contadorEconomicos + contadorEconomicosPremiun + contadorBusiness + contadorPrimeraClase;
    }

    @PostPersist
    public void inicializarCodigo(){
        this.codigo = this.aerolinea.getSiglas() + this.idVuelo;
    }
}
