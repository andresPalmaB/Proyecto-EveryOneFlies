package com.betek.ms_flies.model;

import com.betek.ms_flies.model.modelEnum.TipoAsiento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ASIENTOS")
@SequenceGenerator(name = "asientos_seq", sequenceName = "asientos_sequence", allocationSize = 1)
public class Asiento {

    @Id
    @Column(name = "ID_ASIENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asientos_seq")
    private int idAsiento;

    //añadir atributo codigo de vuelo
    @Column(name = "ID_VUELO")
    private Integer asientoVuelo;

    @Column(name = "DISPONIBILIDAD")
    private boolean disponible;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ASIENTO")
    private TipoAsiento tipoAsiento;

    public Asiento(Integer asientoVuelo, boolean disponible, TipoAsiento tipoAsiento) {
        this.asientoVuelo = asientoVuelo;
        this.disponible = disponible;
        this.tipoAsiento = tipoAsiento;
    }
}
