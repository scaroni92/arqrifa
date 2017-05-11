package org.arqrifa.logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        notificar(mensaje);
    }

    private static void notificar(Object mensajes) throws MessagingException {
        Mensajeria mensajeria = new Mensajeria();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {

                    if (mensajes instanceof DTMensaje) {
                        mensajeria.enviar((DTMensaje) mensajes);
                    } else {
                        for (DTMensaje mensaje : (List<DTMensaje>) mensajes) {
                            mensajeria.enviar(mensaje);
                        }
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
