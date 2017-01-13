package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.exceptions.ArquitecturaRifaException;
import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.VMUsuarioMantenimiento;
import org.arqrifa.viewmodels.VMVerificacion;
import org.arqrifa.viewmodels.ViewModel;

public class ControladorUsuarios extends Controlador {

    public void login_post() {
        try {
            DTUsuario usuario = cliente.login(Integer.parseInt(request.getParameter("ci")), request.getParameter("pass"));
            if (usuario == null) {
                throw new Exception("Usuario o contraseña incorrectos.");
            }

            sesion.setAttribute("usuario", usuario);
            mostrarVista(usuario.getRol() + "/index.jsp");
        } catch (Exception ex) {
            mostrarVista("login.jsp", new ViewModel(ex.getMessage()));
        }
    }

    public void registrar_get() {
        VMUsuarioMantenimiento vm = new VMUsuarioMantenimiento();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje("Error al listar las generaciones.");
        }
        mostrarVista("Estudiante/registro.jsp", vm);
    }

    public void registrar_post() {
        VMUsuarioMantenimiento vm = (VMUsuarioMantenimiento) cargarModelo(new VMUsuarioMantenimiento());
        List<DTGeneracion> generaciones = new ArrayList();
        try {
            generaciones = cliente.listarGeneraciones();
            int ci = Integer.parseInt(vm.getCi());
            int generacion = Integer.parseInt(vm.getGeneracion());

            if (vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getEmail().isEmpty()) {
                throw new ArquitecturaRifaException("Completa todos los campos obligatorios.");
            }

            cliente.agregarSolicitud(new DTSolicitud(ci, new Date(), false, new DTUsuario(ci, vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "", generacion)));
            mostrarVista("login.jsp", new ViewModel("Solicitud enviada exitosamente. <br> Se ha enviado un mail de verificación a tu correo electrónico."));

        } catch (Exception ex) {
            vm.setGeneraciones(generaciones);
            vm.setMensaje(ex.getMessage());
            mostrarVista("Estudiante/registro.jsp", vm);
        }
    }

    public void verificar_get() {
        VMVerificacion vm = new VMVerificacion();
        try {
            int codigo = Integer.parseInt(request.getParameter("codigo"));

            vm.setVerificada(false);
            cliente.verificarSolicitud(codigo);

            vm.setVerificada(true);
        } catch (NumberFormatException ex) {
            vm.setMensaje("El código de verificación no es válido.");
        } catch (Exception ex) {
            vm.setMensaje("No se pudo verificar la solicitud, quizas haya sido rechazada por el encargado");
        }
        mostrarVista("Estudiante/verificar_solicitud.jsp", vm);
    }

    public void ver_get() {
        VMUsuario vm = new VMUsuario();
        try {

            DTUsuario usuario = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));
            
            if (getUsuario().getRol().equals(DTUsuario.ENCARGADO) && getUsuario().getGeneracion() != usuario.getGeneracion()) {
                throw new Exception("Usuario no encontrado");
            }

            vm.setUsuario(usuario);
            if (usuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                vm.setInasistencias(contarInasistencias(usuario));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Estudiante/ver.jsp", vm);
    }

    public void listar_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            if (getUsuario().getRol().equals(DTUsuario.ADMIN)) {
                vm.setUsuarios(cliente.listarUsuarios());
            } else if (getUsuario().getRol().equals(DTUsuario.ENCARGADO)) {
                vm.setUsuarios(cliente.listarEstudiantesPorGeneracion(getUsuario().getGeneracion()));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Usuario/listado.jsp", vm);
    }

    private int contarInasistencias(DTUsuario usuario) throws Exception {

        int inasistencias = 0;
        List<DTReunion> reuniones = cliente.listarReunionesPorGeneracion(usuario.getGeneracion());

        boolean esParticipante;
        for (DTReunion reunion : reuniones) {
            if (reunion.getEstado().equals(DTReunion.FINALIZADA)) {
                esParticipante = false;
                for (DTUsuario participante : reunion.getParticipantes()) {
                    if (participante.getCi() == usuario.getCi()) {
                        esParticipante = true;
                        break;
                    }
                }
                if (!esParticipante) {
                    inasistencias++;
                }
            }
        }
        return inasistencias;
    }
}
