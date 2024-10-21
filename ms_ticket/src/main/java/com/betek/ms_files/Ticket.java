package com.betek.ms_files;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket; // Cambiado a Long
    private String idPassenger;
    private String flightInfo;
    private LocalDate issueDate; // Usando LocalDate
    private TicketStatus ticketStatus; // Usando enum

    // Getters y setters
    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(String idPassenger) {
        this.idPassenger = idPassenger;
    }

    public String getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(String flightInfo) {
        this.flightInfo = flightInfo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "idTicket=" + idTicket +
                ", idPassenger='" + idPassenger + '\'' +
                ", flightInfo='" + flightInfo + '\'' +
                ", issueDate=" + issueDate +
                ", ticketStatus=" + ticketStatus +
                '}';
    }
}

enum TicketStatus {
    CONFIRMADO,
    CHECK_IN,
    CANCELADO
}
