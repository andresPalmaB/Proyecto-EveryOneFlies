package com.betek.everyOneFlies.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "PASSENGER")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASSENGER")
    private Long idPassenger;

    @ManyToOne
    @JoinColumn(name = "ID_RESERVACION", referencedColumnName = "ID_RESERVACION", nullable = false)
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
    @Pattern(regexp = "^\\+\\d{1,3}-\\d{7,15}$", message = "must have the format +123-xxxxxxxx (country code of up to three digits followed by a number of 7 to 15 digits)")
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
        if (isResponsibleForPayment){
            return  "Firstname: " + firstName + '\n' +
                    "Lastname:  " + lastName + '\n' + '\n' +

                    "Reserve Information:  " + '\n' +
                    "Code:   " + reserve.getReserveCode() + '\n' +
                    "Date:   " + reserve.getReservationDate() + '\n' +
                    "Status: " + reserve.getStatus() + '\n' + '\n' +

                    "Flight Information:" + '\n' +
                    reserve.getFlight() + '\n' + '\n' +

                    "Seat Information:" + '\n' +
                    reserve.getSeat();
        } else {
            return  "Firstname: " + firstName + '\n' +
                    "Lastname:  " + lastName + '\n' + '\n' +

                    "Flight Information:" + '\n' +
                    reserve.getFlight() + '\n' + '\n' +

                    "Seat Information:" + '\n' +
                    reserve.getSeat();
        }
    }
}