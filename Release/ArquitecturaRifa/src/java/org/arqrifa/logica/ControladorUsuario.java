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
        String link = "http://localhost:8080/ArquitecturaRifaWeb/verificar?codigo=";
        
        try {
            if (solicitud == null) {
                throw new Exception("No se puede dar de alta una solicitud nula.");
            }
            if (solicitud.getCi() < 4000000) {
                throw new Exception("Cédula inválida.");
            }
            
            solicitud.setCodigo((int) (new Random().nextDouble() * 99999999));
            FabricaPersistencia.getPersistenciaSolicitud().alta(solicitud);

            String msj = solicitud.getNombre() + " tu solicitud ha sido enviada exitosamente, ahora solo"
                + " falta que verifiques tu dirección de correo electrónico haciendo clic en este enlace:\n "
              + link + solicitud.getCodigo();
            
            new Mensajeria(new Mensaje(solicitud.getEmail(), "Confirmar registro", msj)).enviar();
            System.out.println("XX-X-X-X-X--XX-X-X--X-X-X-X-X-X- mail de confirmación enviado. X.XX-X-X-X-X--X-X-X-X-X-X--X-");
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }

    @Override
    public void verificarSolicitud(int codigo) {
        try {
            FabricaPersistencia.getPersistenciaSolicitud().verificar(codigo);
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
            usuario.setRol("Encargado");
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
            DTUsuario usuario = new DTUsuario(s.getCi(), s.getNombre(), s.getApellido(), s.getContrasena(), s.getEmail(), "Estudiante", s.getGeneracion());
            FabricaPersistencia.getPersistenciaSolicitud().confirmar(s);
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
            FabricaPersistencia.getPersistenciaSolicitud().rechazar(solicitud);
        } catch (Exception e) {
            throw new ArquitecturaRifaExcepcion(e.getMessage());
        }
    }
}
