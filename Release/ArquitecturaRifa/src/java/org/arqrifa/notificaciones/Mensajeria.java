package org.arqrifa.notificaciones;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.arqrifa.datatypes.DTMensaje;

public class Mensajeria {

    private final String EMISOR = "sgdr.arquitecturarifa@outlook.com";
    private final String CONTRASENA = "arqrifa1234";
    private Session sesion = null;

    public Mensajeria() throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.live.com");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        autenticar(properties);
    }

    private void autenticar(Properties properties) throws MessagingException, NoSuchProviderException {
        sesion = Session.getInstance(properties, new Authenticator() {
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

    public void enviar(DTMensaje mensaje) throws MessagingException {
        Message msg = new MimeMessage(sesion);
        msg.setFrom(new InternetAddress(EMISOR));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensaje.getDestinatario()));
        msg.setSubject(mensaje.getAsunto());
        msg.setText(mensaje.getMensaje());
        Transport.send(msg);
    }
}
