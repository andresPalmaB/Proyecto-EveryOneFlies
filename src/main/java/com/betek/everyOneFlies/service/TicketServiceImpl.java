package com.betek.everyOneFlies.service;

import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import com.betek.everyOneFlies.model.Flight;
import com.betek.everyOneFlies.repository.FlightRepository;
import com.betek.everyOneFlies.model.Ticket;
import com.betek.everyOneFlies.dto.dtoModel.TicketDTO;
import com.betek.everyOneFlies.model.modelEnum.TicketStatus;
import com.betek.everyOneFlies.repository.TicketRepository;
import com.betek.everyOneFlies.service.serviceInterface.TicketService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.betek.everyOneFlies.model.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private FlightRepository flightRepository;

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) {

        if(repository.findTicketByReserve(ticketDTO.reserve()).isPresent()){
            throw new ResourceNotFoundException(
                    "El Tique ya existe."
            );
        }

        return repository.save(
                new Ticket(
                        ticketDTO.reserve(),
                        ticketDTO.issueDate(),
                        ticketDTO.ticketStatus()
                )
        );
    }

    @Override
    public Ticket generateTicketPdf(Long ticketId) {
        // Obtener el ticket por ID
        Ticket ticket = repository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        Path pdfPath = Paths.get("pdf", "ticket_" + ticket.getIdTicket() + ".pdf");
        this.createPdf(ticket, pdfPath.toString());

        // Actualizar el estado del ticket
        ticket.setTicketStatus(TicketStatus.CONFIRMADO);
        return repository.save(ticket); // Guardar los cambios en el ticket
    }

    private void createPdf(Ticket ticket, String pdfPath) {

        Flight flight = flightRepository.findById(ticket.getReserve().getFlightId())
                .orElseThrow(()-> new ResourceNotFoundException("Vuelo no encontrado"));

        try {
            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Agregar contenido al PDF
            document.add(new Paragraph("Ticket ID: " + ticket.getIdTicket()));
            document.add(new Paragraph("Fecha de emisión: " + ticket.getIssueDate()));
            document.add(new Paragraph("Estado: " + ticket.getTicketStatus()));
            document.add(new Paragraph("Información Reserva: "));
            document.add(new Paragraph("    Pasajero/s: "));

            for(Passenger pasajero : ticket.getReserve().getPassengers()){
                document.add(new Paragraph(String.valueOf(pasajero)));
            }

            document.add(new Paragraph("Información Vuelo:"));
            document.add(new Paragraph("    Código Vuelo:       " + flight.getFlightCode()));
            document.add(new Paragraph("    Nombre Aerolínea:   " + flight.getAirline().getName()));
            document.add(new Paragraph("    Sigla Aerolínea:    " + flight.getAirline().getAcronym()));
            document.add(new Paragraph("    Aeropuerto Origen:"));
            document.add(new Paragraph("        Código IATA:    " + flight.getOrigin().getIataCode()));
            document.add(new Paragraph("        Nombre:         " + flight.getOrigin().getName()));
            document.add(new Paragraph("        Ciudad:         " + flight.getOrigin().getLocation().getCity()));
            document.add(new Paragraph("        Fecha Salida:   " + flight.getDepartureDate()));
            document.add(new Paragraph("        Hora Salida:    " + flight.getDepartureTime()));
            document.add(new Paragraph("    Aeropuerto Destino:"));
            document.add(new Paragraph("        Código IATA:    " + flight.getDestination().getIataCode()));
            document.add(new Paragraph("        Nombre:         " + flight.getDestination().getName()));
            document.add(new Paragraph("        Ciudad:         " + flight.getDestination().getLocation().getCity()));
            document.add(new Paragraph("        Fecha Salida:   " + flight.getArrivalDate()));
            document.add(new Paragraph("        Hora Salida:    " + flight.getArrivalTime()));

            // Cerrar el documento
            document.close();
        } catch (FileNotFoundException e) {
            logger.error("Error al crear el PDF para el ticket con ID: {}", ticket.getIdTicket(), e);
        }
    }


    @Override
    public Ticket updateTicket(Ticket ticket) {

        Ticket found = repository.findById(ticket.getIdTicket())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        found.setTicketStatus(ticket.getTicketStatus());
        found.setReserve(ticket.getReserve());

        Path pdfPath = Paths.get("pdf", "ticketUpdate_" + ticket.getIdTicket() + ".pdf");
        createPdf(found, pdfPath.toString());

        return repository.save(found);
    }

    @Override
    public Ticket getTicketById(long idticket){
        return repository.findById(idticket)
                .orElseThrow(()-> new ResourceNotFoundException("Tickeet no encontrado"));
    }
}
