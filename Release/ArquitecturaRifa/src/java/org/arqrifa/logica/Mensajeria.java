package org.arqrifa.logica;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.arqrifa.datatypes.DTSolicitud;

public class Mensajeria {

    private Session sesion = null;
    private final Properties propiedades = new Properties();
    private final String EMISOR = "arquitectura_rifa@hotmail.com";
    private final String CONTRASENA = "arqrifa123";
    private final String ASUNTO;
    private final String MENSAJE;
    private final String DESTINATARIO;
    private final String RUTA = "http://localhost:8080/ArquitecturaRifaWeb/verificar?";

    public Mensajeria(DTSolicitud solicitud) throws MessagingException {
        this.ASUNTO = "Confirmar registro";
        this.DESTINATARIO = solicitud.getEmail();
        this.MENSAJE = solicitud.getNombre() + " tu solicitud ha sido enviada exitosamente, ahora solo"
                + " falta que verifiques tu dirección de correo electrónico haciendo clic en este enlace:\n "
                + RUTA + "ci=" + solicitud.getCi();
        
        this.autenticar();
    }

    private void autenticar() throws MessagingException {
        propiedades.put("mail.transport.protocol", "smtp");
        propiedades.put("mail.smtp.host", "smtp.live.com");
        propiedades.put("mail.smtp.socketFactory.port", "587");
        propiedades.put("mail.smtp.socketFactory.fallback", "false");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.port", "587");

        sesion = Session.getInstance(propiedades,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMISOR, CONTRASENA);
            }
        });
        sesion.setDebug(false);

        Transport transport = sesion.getTransport("smtp");
        if (transport != null) {
            transport.connect();
        }
    }

    public void enviar() throws MessagingException {
        Message msg = new MimeMessage(sesion);
        msg.setFrom(new InternetAddress(EMISOR));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DESTINATARIO));
        msg.setSubject(ASUNTO);
        msg.setText(MENSAJE);
        Transport.send(msg);
    }
}
