package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.model.Passenger;
import com.betek.everyOneFlies.model.Reserve;
import com.betek.everyOneFlies.model.modelEnum.ReservationStatus;
import com.betek.everyOneFlies.repository.FlightRepository;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.repository.TicketRepository;
import com.betek.everyOneFlies.service.serviceInterface.PassengerService;
import com.betek.everyOneFlies.service.serviceInterface.ReserveService;
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

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public Ticket createTicket(TicketDTO ticketDTO) {

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
            throw new ResourceNotFoundException(
                    "Ticket already exist."
            );
        }

        return repository.save(
                new Ticket(
                        ticketDTO.passenger(),
                        ticketDTO.issueDate(),
                        ticketDTO.ticketStatus()
                )
        );
    }

    @Override
    public Ticket generateTicketPdf(Long ticketId) {

        Ticket ticket = repository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found."));

        Path pdfPath = Paths.get("pdf", "ticket_" + ticket.generateNameTicket() + ".pdf");
        this.createPdf(ticket, pdfPath.toString());

        ticket.setTicketStatus(TicketStatus.CONFIRMED);
        return repository.save(ticket);

    }

    private void createPdf(Ticket ticket, String pdfPath) {

        try {
            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Agregar contenido al PDF
            document.add(new Paragraph("Ticket ID: " + ticket.getIdTicket()));
            document.add(new Paragraph("Issue Date: " + ticket.getIssueDate()));
            document.add(new Paragraph("Status: " + ticket.getTicketStatus()));
            document.add(new Paragraph("Passenger Information: "));
            document.add(new Paragraph(String.valueOf(ticket.getPassenger())));

            // Cerrar el documento
            document.close();
        } catch (FileNotFoundException e) {
            logger.error("Error al crear el PDF para el ticket con ID: {}", ticket.getIdTicket(), e);
        }
    }

    @Override
    public Ticket getTicketById(Long idTicket){
        return repository.findById(idTicket).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Ticket with ID " + idTicket + "not found.")
        );
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {

        Ticket found = this.getTicketById(ticket.getIdTicket());

        found.setTicketStatus(ticket.getTicketStatus());
        found.setPassenger(ticket.getPassenger());

        Path pdfPath = Paths.get("pdf", "ticketUpdate_" + ticket.generateNameTicket() + ".pdf");
        createPdf(found, pdfPath.toString());

        return repository.save(found);
    }

}
