package com.betek.everyOneFlies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PASAJERO")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASAJERO")
    private Long idPassenger;

    @ManyToOne
    @JoinColumn(name = "ID_RESERVACION", referencedColumnName = "ID_RESERVACION", nullable = false)
    private Reserve reserve;

    @Column(name = "ES_RESPONSABLE", nullable = false)
    private boolean isResponsibleForPayment;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    @Column(name = "phone", nullable = false)
    private String phone;

    public Passenger(Reserve reserve, boolean isResponsibleForPayment, String firstName,
                     String lastName, String email, String phone) {
        this.reserve = reserve;
        this.isResponsibleForPayment = isResponsibleForPayment;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  "       Nombre:    " + firstName + '\n' +
                "       Apellido:  " + lastName + '\n' +
                "       Email:     " + email + '\n' +
                "       Telefono:  " + phone + '\n';
    }
}