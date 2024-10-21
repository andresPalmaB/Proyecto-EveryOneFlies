package com.betek.ms_files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.betek.ms_files")
public class Main_Tickets_Management implements CommandLineRunner {

	@Autowired
	private Ticket_Manager ticket_manager;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private UserRepository userRepository; // Asegúrate de que este repositorio esté importado y definido

	public static void main(String[] args) {
		SpringApplication.run(Main_Tickets_Management.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Generación de tickets
		Ticket ticket1 = ticket_manager.generateTicket("1001200445", "Vuelo 1234 a Bogotá");
		Ticket ticket2 = ticket_manager.generateTicket("80733774", "Vuelo 5678 a Medellín");

		// Mostrar todos los tickets
		ticket_manager.displayTickets();

		// Check-in para el ticket
		boolean checkInSuccess = ticket_manager.checkInTicket(ticket1.getIdTicket());
		System.out.println(checkInSuccess ? "Check-in successful." : "Check-in failed.");

		// Asume que ya tienes el idPassenger disponible, por ejemplo, desde el ticket o como argumento
		int idPassenger = Integer.parseInt(ticket1.getIdPassenger()); // Asegúrate de que tienes este método en Ticket

		// Obtener el usuario
		User user = userRepository.findByIdPassenger(idPassenger);
		if (user == null) {
			System.out.println("Usuario no encontrado");
			return; // Detener la ejecución si el usuario no se encuentra
		}

		// Generar el PDF para el ticket
		byte[] pdfBytes = pdfGenerator.generateBoardingPassPDF(ticket1, user);

		// Enviar el correo con el PDF adjunto
		emailSender.sendEmailWithAttachment(user.getEmail(), "Your Boarding Pass", "Please find attached your boarding pass.", pdfBytes);
	}
}
