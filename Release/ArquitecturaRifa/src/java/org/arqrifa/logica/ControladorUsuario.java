package org.arqrifa.logica;

import java.util.Random;
import javax.mail.MessagingException;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.persistencia.FabricaPersistencia;
import org.arqrifa.excepciones.ArquitecturaRifaExcepcion;

class ControladorUsuario implements IControladorUsuario {

    private static ControladorUsuario instancia = null;

    public static IControladorUsuario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorUsuario();
        }
        return instancia;
    }

    private ControladorUsuario() {
    }

    @Override
    public DTUsuario Autenticar(int ci, String contrasena) {
        DTUsuario resp = null;
        try {
            resp = FabricaPersistencia.getPersistenciaUsuario().autenticar(ci, contrasena);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
        return resp;
    }

    @Override
    public void altaSolicitud(DTSolicitud solicitud) {
        try {
            if (solicitud == null) {
                throw new Exception("No se puede dar de alta una solicitud nula.");
            }
            if (solicitud.getCi() < 4000000) {
                throw new Exception("Cédula inválida.");
            }

            int codigo = (int) (new Random().nextDouble() * 99999999);
            solicitud.setCodigo(codigo);
            FabricaPersistencia.getPersistenciaSolicitud().altaSolicitud(solicitud);
            new Mensajeria(solicitud).enviar();
            System.out.println("mail de confirmación enviado.");
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public void verificarSolicitud(int codigo) {
        try {
            FabricaPersistencia.getPersistenciaSolicitud().verificarSolicitud(codigo);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public void altaEncargado(DTUsuario usuario) {
        try {
            if (usuario == null) {
                throw new Exception("No se puede dar de alta una solicitud nula.");
            }
            if (usuario.getCi() < 4000000) {
                throw new Exception("Cédula inválida.");
            }
            usuario.setRol("encargado");
            FabricaPersistencia.getPersistenciaUsuario().altaUsuario(usuario);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public void confirmarSolicitud(DTSolicitud s) {
        try {
            if (s == null) {
                throw new Exception("No se puede confirmar una solicitud nula.");
            }
            if (!s.isVerificada()) {
                throw new Exception("No se puede confirmar una solicitud sin verificar");
            }
            DTUsuario usuario = new DTUsuario(s.getCi(), s.getNombre(), s.getApellido(), s.getContrasena(), s.getEmail(), "estudiante", s.getGeneracion());
            FabricaPersistencia.getPersistenciaSolicitud().confirmarSolicitud(s);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public void rechazarSolicitud(DTSolicitud solicitud) {
        try {
            if (solicitud == null) {
                throw new Exception("No se puede rechazar una solicitud nula.");
            }
            FabricaPersistencia.getPersistenciaSolicitud().rechazarSolicitud(solicitud);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }
}
