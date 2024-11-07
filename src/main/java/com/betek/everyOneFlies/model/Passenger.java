package com.betek.everyOneFlies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PASSENGER")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASSENGER")
    private Long idPassenger;

    @ManyToOne
    @JoinColumn(name = "ID_RESERVE", referencedColumnName = "ID_RESERVER", nullable = false)
    @NotNull(message = "Reserve is mandatory")
    private Reserve reserve;

    @Column(name = "IS_RESPONSABLE", nullable = false)
    @NotNull(message = "Responsible for payment is mandatory")
    private Boolean isResponsibleForPayment;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 1, max = 50, message = "First Name must be between 1 and 50 characters")
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max = 50, message = "Last Name must be between 1 and 50 characters")
    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    @Column(name = "PHONE", nullable = false)
    private String phone;

    public Passenger(Reserve reserve, Boolean isResponsibleForPayment, String firstName,
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
        return  "Nombre:    " + firstName + '\n' +
                "Apellido:  " + lastName + '\n' +
                "Email:     " + email + '\n' +
                "Telefono:  " + phone + '\n';
    }
}