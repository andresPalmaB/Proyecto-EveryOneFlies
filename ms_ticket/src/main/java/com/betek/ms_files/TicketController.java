package com.betek.ms_files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository; // Corregido: quitar "Ticket." antes de TicketRepository

    @Autowired
    private UserRepository userRepository; // Corregido: quitar "User." antes de UserRepository

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private EmailSender emailSender; // Asegúrate de que EmailSender esté correctamente definido

    @GetMapping("/send-boarding-pass/{idPassenger}")
    public String sendBoardingPass(@PathVariable int idPassenger) {
        // Obtener el usuario por su id_passenger
        User user = userRepository.findByIdPassenger(idPassenger);
        // Obtener la lista de tickets para el usuario
        List<Ticket> tickets = ticketRepository.findByIdPassenger(String.valueOf(idPassenger));

        // Verificar si el usuario y los tickets existen
        if (user == null || tickets.isEmpty()) {
            return "Usuario o tickets no encontrados";
        }

        // Generar el PDF para el primer ticket (puedes ajustar según necesidad)
        byte[] pdfBytes = pdfGenerator.generateBoardingPassPDF(tickets.get(0), user);

        // Enviar el PDF por correo
        emailSender.sendEmailWithAttachment(user.getEmail(), "Pase de abordar", "Su pase de abordar está adjunto.", pdfBytes);

        return "Pase de abordar enviado a " + user.getEmail();
    }
}
