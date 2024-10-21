package com.betek.ms_files;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

@Service
public class EmailSender {

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] pdfBytes) {
        // Configuración de propiedades del servidor de correo
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Datos de autenticación
        final String username = System.getenv("EMAIL_USERNAME");
        final String password = System.getenv("EMAIL_PASSWORD");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Cuerpo del mensaje
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");

            // Adjuntar archivo PDF (desde el byte array)
            MimeBodyPart attachmentPart = new MimeBodyPart();
            var dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
            attachmentPart.setDataHandler(new DataHandler((DataSource) dataSource));
            attachmentPart.setFileName("boarding-pass.pdf");

            // Agrupar las partes en un objeto Multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Establecer el contenido del mensaje
            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);

            System.out.println("Correo enviado exitosamente con el pase de abordar adjunto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
