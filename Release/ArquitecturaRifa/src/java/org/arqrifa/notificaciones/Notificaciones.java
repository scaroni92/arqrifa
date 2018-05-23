package org.arqrifa.notificaciones;

import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTMensaje;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.logica.FabricaLogica;
import java.util.ArrayList;
import java.util.List;

public class Notificaciones {

    public static void notificarNuevaReunion(DTReunion reunion) throws MessagingException, Exception {
        String destinatario, asunto = NotificacionUtil.NUEVA_REUNION_ASUNTO, mensaje, fecha = NotificacionUtil.DateToString(reunion.getFecha());
        List<DTMensaje> mensajes = new ArrayList();
        for (DTUsuario estudiante : FabricaLogica.getLogicaUsuario().listarEstudiantes(reunion.getGeneracion())) {
            destinatario = estudiante.getEmail();
            mensaje = String.format(NotificacionUtil.NUEVA_REUNION_MENSAJE, estudiante.getNombre(), fecha);
            mensajes.add(new DTMensaje(destinatario, asunto, mensaje));
        }
        NotificadorMail.getInstancia().notificar(mensajes);
    }

    public static void notificarSolicitudAgregada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_AGREGADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_AGREGADA_MENSAJE, solicitud.getUsuario().getNombre(), solicitud.getCodigo());
        NotificadorMail.getInstancia().notificar(new DTMensaje(destinatario, asunto, mensaje));
    }

    public static void notificarSolicitudAceptada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_ACEPTADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_ACEPTADA_MENSAJE, solicitud.getUsuario().getNombre());
        NotificadorMail.getInstancia().notificar(new DTMensaje(destinatario, asunto, mensaje));
    }

    public static void notificarSolicitudRechazada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_RECHAZADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_RECHAZADA_MENSAJE, solicitud.getUsuario().getNombre());
        NotificadorMail.getInstancia().notificar(new DTMensaje(destinatario, asunto, mensaje));
    }

}
