package org.arqrifa.logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTMensaje;

public class Util {

    public static Date formatearFecha(Date fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(fecha));
    }

    public static int compararFechas(Date fecha1, Date fecha2) throws ParseException {
        return formatearFecha(fecha1).compareTo(formatearFecha(fecha2));
    }

    public static void notificarMail(List<DTMensaje> mensajes) throws MessagingException {
        notificar(mensajes);
    }

    public static void notificarMail(DTMensaje mensaje) throws MessagingException {
        List<DTMensaje> mensajes = new ArrayList<>();
        mensajes.add(mensaje);
        notificar(mensajes);
    }

    private static void notificar(List<DTMensaje> mensajes) throws MessagingException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    Mensajeria mensajeria = new Mensajeria();
                    for (DTMensaje mensaje : mensajes) {
                        mensajeria.enviar(mensaje);
                    }

                } catch (MessagingException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
