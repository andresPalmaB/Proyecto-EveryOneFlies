package com.betek.ms_files;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
@Component
public class PdfGenerator {
    public byte[] generateBoardingPassPDF(Ticket ticket, User user) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            // Aquí agrega el contenido del PDF
            document.add(new Paragraph("Boarding Pass"));
            document.add(new Paragraph("Passenger: " + user.getEmail())); // Ajusta según los campos del usuario
            document.add(new Paragraph("Ticket ID: " + ticket.getIdTicket())); // Ajusta según los campos del ticket
            // Agrega más información según sea necesario
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray(); // Retorna el contenido del PDF como un array de bytes
    }

}
