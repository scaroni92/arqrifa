/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arqrifa.notificaciones;

import java.util.List;
import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTMensaje;

/**
 *
 * @author Ale
 */
public class NotificadorMail {

    private static NotificadorMail instancia = null;
    private Mensajeria mensajeria;

    public static NotificadorMail getInstancia() {
        if (instancia == null) {
            instancia = new NotificadorMail();
        }
        return instancia;
    }

    private NotificadorMail() {
        try {
            mensajeria = new Mensajeria();
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void notificar(List<DTMensaje> mensajes) throws MessagingException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!mensajes.isEmpty()) {
                        for (DTMensaje mensaje : mensajes) {
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

    public void notificar(DTMensaje mensaje) throws MessagingException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mensajeria.enviar(mensaje);
                } catch (MessagingException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
