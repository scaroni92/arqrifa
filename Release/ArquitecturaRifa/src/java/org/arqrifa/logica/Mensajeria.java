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
import org.arqrifa.datatypes.DTMensaje;

public class Mensajeria {

    private final Properties PROPIEDADES = new Properties();
    private final String EMISOR = "arquitectura_rifa@hotmail.com";
    private final String CONTRASENA = "arqrifa123";
    private Session sesion = null;
    private DTMensaje mensaje;

    public DTMensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(DTMensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Mensajeria(DTMensaje mensaje) throws MessagingException {
        this.mensaje = mensaje;
        this.autenticar();
    }

    private void autenticar() throws MessagingException {
        PROPIEDADES.put("mail.transport.protocol", "smtp");
        PROPIEDADES.put("mail.smtp.host", "smtp.live.com");
        PROPIEDADES.put("mail.smtp.socketFactory.port", "587");
        PROPIEDADES.put("mail.smtp.socketFactory.fallback", "false");
        PROPIEDADES.put("mail.smtp.starttls.enable", "true");
        PROPIEDADES.put("mail.smtp.auth", "true");
        PROPIEDADES.put("mail.smtp.port", "587");

        sesion = Session.getInstance(PROPIEDADES, new Authenticator() {
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
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensaje.getDestinatario()));
        msg.setSubject(mensaje.getAsunto());
        msg.setText(mensaje.getMensaje());
        Transport.send(msg);
    }
}
