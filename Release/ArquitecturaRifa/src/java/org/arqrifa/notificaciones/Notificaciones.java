/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arqrifa.notificaciones;


import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTMensaje;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.logica.FabricaLogica;

/**
 *
 * @author Ale
 */
public class Notificaciones {

    public static void notificarNuevaReunion(DTReunion reunion) throws MessagingException, Exception {
        //delegar creación
        String destinatario, asunto = NotificacionUtil.NUEVA_REUNION_ASUNTO, mensaje, fecha = NotificacionUtil.DateToString(reunion.getFecha());

        for (DTUsuario estudiante : FabricaLogica.getLogicaUsuario().listarEstudiantes(reunion.getGeneracion())) {
            destinatario = estudiante.getEmail();
            mensaje = String.format(NotificacionUtil.NUEVA_REUNION_MENSAJE, estudiante.getNombre(), fecha);
            NotificadorMail.notificar(new DTMensaje(destinatario, asunto, mensaje));
        }

    }
    
    public static void notificarSolicitudAgregada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_AGREGADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_AGREGADA_MENSAJE, solicitud.getUsuario().getNombre(), solicitud.getCodigo());
        NotificadorMail.notificar(new DTMensaje(destinatario, asunto, mensaje));
    }

    public static void notificarSolicitudAceptada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_ACEPTADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_ACEPTADA_MENSAJE, solicitud.getUsuario().getNombre());
        NotificadorMail.notificar(new DTMensaje(destinatario, asunto, mensaje));
    }
    
    public static void notificarSolicitudRechazada(DTSolicitud solicitud) throws MessagingException, Exception {
        String destinatario = solicitud.getUsuario().getEmail();
        String asunto = NotificacionUtil.SOLICITUD_RECHAZADA_ASUNTO;
        String mensaje = String.format(NotificacionUtil.SOLICITUD_RECHAZADA_MENSAJE, solicitud.getUsuario().getNombre());
        NotificadorMail.notificar(new DTMensaje(destinatario, asunto, mensaje));
    }

}