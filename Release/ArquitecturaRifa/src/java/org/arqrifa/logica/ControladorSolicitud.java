package org.arqrifa.logica;

import java.util.List;
import java.util.Random;
import org.arqrifa.datatypes.DTMensaje;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.persistencia.FabricaPersistencia;

class ControladorSolicitud implements IControladorSolicitud {

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    private static ControladorSolicitud instancia = null;

    public static ControladorSolicitud getInstancia() {
        if (instancia == null) {
            instancia = new ControladorSolicitud();
        }
        return instancia;
    }

    private ControladorSolicitud() {
    }
    //</editor-fold>

    @Override
    public void agregarSolicitud(DTSolicitud solicitud) {
        try {
            if (solicitud == null) {
                throw new Exception("No se puede dar de alta una solicitud nula.");
            }
            if (solicitud.isVerificada()) {
                throw new Exception("No se puede aceptar una solicitud sin verificar.");
            }

            solicitud.setCodigo((int) (new Random().nextDouble() * 99999999));

            FabricaPersistencia.getPersistenciaSolicitud().agregar(solicitud);

            String destinatario = solicitud.getUsuario().getEmail();
            String asunto = "Arquitectura Rifa - Confirmar registro";
            String mensaje = "Hola " + solicitud.getUsuario().getNombre()
                    + " tu solicitud ha sido enviada exitosamente, ahora solo"
                    + " falta que verifiques tu dirección de correo electrónico haciendo clic en este enlace:\n "
                    + "http://localhost:8080/ArquitecturaRifaWeb/index?accion=verificar&codigo=" + solicitud.getCodigo();
            
            //enviarNotificacionMail(new DTMensaje(destinatario, asunto, mensaje));
            Util.notificarMail(new DTMensaje(destinatario, asunto, mensaje));

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void verificarSolicitud(int codigo) {
        try {
            FabricaPersistencia.getPersistenciaSolicitud().verificar(codigo);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void confirmarSolicitud(DTSolicitud solicitud) {
        try {
            if (solicitud == null) {
                throw new Exception("No se puede confirmar una solicitud nula.");
            }
            if (!solicitud.isVerificada()) {
                throw new Exception("No se puede confirmar una solicitud sin verificar");
            }
            FabricaPersistencia.getPersistenciaSolicitud().confirmar(solicitud);

            // Mail de notifiación
            String destinatario = solicitud.getUsuario().getEmail();
            String asunto = "Arquitectura Rifa - Registro completado";
            String mensaje = "Hola " + solicitud.getUsuario().getNombre() + ",\n\n"
                    + "Tu solicitud ha sido confirmada, ya puedes iniciar sesión en "
                    + "http://localhost:8080/ArquitecturaRifaWeb con tu cédula y contraseña. \n\n"
                    + "Para descargar la apliación móvil haz click en el siguiente enlace:\n http://.........................";

            Util.notificarMail(new DTMensaje(destinatario, asunto, mensaje));

        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public void rechazarSolicitud(DTSolicitud solicitud) {
        try {
            if (solicitud == null) {
                throw new Exception("No se puede rechazar una solicitud nula.");
            }
            FabricaPersistencia.getPersistenciaSolicitud().rechazar(solicitud);

            // Mail de notifiación
            DTMensaje mensaje = new DTMensaje();
            mensaje.setDestinatario(solicitud.getUsuario().getEmail());
            mensaje.setAsunto("Arquitectura Rifa - Registro rechazado");
            mensaje.setMensaje("Hola " + solicitud.getUsuario().getNombre()
                    + ",\n\nTe informamos que tu solicitud ha sido rechazada, si tienes alguna duda ponte en contacto con el encargado de tu generación.");

            Util.notificarMail(mensaje);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public DTSolicitud buscarSolicitud(int ci) {
        try {
            return FabricaPersistencia.getPersistenciaSolicitud().buscar(ci);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

    @Override
    public List<DTSolicitud> listarSolicitudes(int generacion) {
        try {
            return FabricaPersistencia.getPersistenciaSolicitud().listar(generacion);
        } catch (Exception e) {
            throw new ArquitecturaRifaException(e.getMessage());
        }
    }

}
