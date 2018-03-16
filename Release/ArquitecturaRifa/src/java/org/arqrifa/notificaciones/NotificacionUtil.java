/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arqrifa.notificaciones;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ale
 */
class NotificacionUtil {

    static final String URL_ARQUITECTURA_RIFA_WEB = "http://localhost:8080/ArquitecturaRifaWeb/";
    static final String URL_VERIFICAR_SOLICITUD = URL_ARQUITECTURA_RIFA_WEB + "index?accion=verificar&codigo=";
    static final String URL_PLAYSTORE_DESCARGA = "http://www.playstore.com";

    static final String SOLICITUD_AGREGADA_ASUNTO = "Confirma tu correo electrónico.";
    static final String SOLICITUD_AGREGADA_MENSAJE = "Hola %1$s, tu solicitud ha sido recibida exitosamente, ahora solo falta que verifiques tu dirección de correo electrónico haciendo clic en este enlace:\n "
            + URL_VERIFICAR_SOLICITUD + "%2$s";
    static final String SOLICITUD_ACEPTADA_ASUNTO = "Solicitud aceptada";
    static final String SOLICITUD_ACEPTADA_MENSAJE = "Hola %1$s,\n\nTu solicitud ha sido confirmada, ya puedes iniciar sesión con tu cédula y contraseña en" + URL_ARQUITECTURA_RIFA_WEB
            + "\n\nPara descargar la aplicación móvil haz click en el siguiente enlace:\n" + URL_PLAYSTORE_DESCARGA;
    static final String SOLICITUD_RECHAZADA_ASUNTO = "Solicitud rechazada";
    static final String SOLICITUD_RECHAZADA_MENSAJE = "Hola %1$s,\n\nTe informamos que tu solicitud ha sido rechazada"
            + ", si tienes alguna duda ponte en contacto con el encargado de tu generación.";

    static final String NUEVA_REUNION_MENSAJE = "Hola %1$s, te informamos que se ha agendado una nueva reunión para el día %2$s.";
    static final String NUEVA_REUNION_ASUNTO = "Nueva reunión agendada";

    static String DateToString(Date date) {
        return new SimpleDateFormat("dd 'de' MMMMM 'a las' HH:mm").format(date);
    }
}
