package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.exception.ExistingResourceException;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.repository.FlightRepository;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.repository.TicketRepository;
import com.betek.everyOneFlies.service.serviceInterface.PassengerService;
import com.betek.everyOneFlies.service.serviceInterface.TicketService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerService passengerService;

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Override
    public void createTicket(TicketDTO ticketDTO) {

        Passenger found = passengerService.getPassengerById(ticketDTO.passenger().getIdPassenger());

        if (!ticketDTO.passenger().equals(found)){
            throw new ResourceNotFoundException(
                    "The passenger data does not match the database. Please check and try again.");
        }

        if (!found.getReserve().getStatus().equals(ReservationStatus.CONFIRMED)){
            throw new ResourceNotFoundException(
                    "The reservation payment must be made.");
        }

        if(repository.findTicketByPassenger(ticketDTO.passenger()).isPresent()){
            throw new ExistingResourceException(
                    "Ticket already exist."
            );
        }

        repository.save(
                new Ticket(
                        ticketDTO.passenger(),
                        ticketDTO.issueDate(),
                        TicketStatus.CONFIRMED
                )
        );
    }

    @Override
    @Transactional
    public byte[] generateAndValidateTicket(Long ticketId, LocalDateTime now) {
        Ticket ticket = repository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found."));

        LocalDate departureDate = ticket.getPassenger().getReserve().getFlight().getDepartureDate();
        LocalTime departureTime = ticket.getPassenger().getReserve().getFlight().getDepartureTime();

        LocalDateTime departureDateTime = departureDate.atTime(departureTime);
        LocalDateTime lowerLimit = departureDateTime.minusHours(24);
        LocalDateTime upperLimit = departureDateTime.minusHours(1);

        if (!ticket.getPassenger().getReserve().getStatus().equals(ReservationStatus.CONFIRMED) ||
                now.isBefore(lowerLimit) || now.isAfter(upperLimit)) {
            throw new IllegalStateException("Ticket cannot be generated at this time.");
        }

        ticket.setTicketStatus(TicketStatus.CHECK_IN);
        repository.save(ticket);

        return createPdf(ticket);
    }

    private byte[] createPdf(Ticket ticket) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Ticket ID: " + ticket.getIdTicket()));
        document.add(new Paragraph("Issue Date: " + ticket.getIssueDate()));
        document.add(new Paragraph("Status: " + ticket.getTicketStatus()));
        document.add(new Paragraph("Passenger Information: "));
        document.add(new Paragraph(String.valueOf(ticket.getPassenger())));

        document.close();
        return outputStream.toByteArray();
    }

    @Override
    public Ticket getTicketById(Long idTicket){
        return repository.findById(idTicket).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Ticket with ID " + idTicket + "not found.")
        );
    }

    @Override
    public Ticket getTicketByPassenger(Passenger passenger){
        return repository.findTicketByPassenger(passenger).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Ticket with passenger " + passenger + "not found."
                )
        );
    }

}
