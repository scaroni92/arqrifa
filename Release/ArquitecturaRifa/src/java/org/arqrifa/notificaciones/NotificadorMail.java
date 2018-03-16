/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arqrifa.notificaciones;

import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTMensaje;

/**
 *
 * @author Ale
 */
public class NotificadorMail {

    public static void notificar(DTMensaje mensaje) throws MessagingException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    new Mensajeria().send(mensaje);
                } catch (MessagingException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };

        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}
