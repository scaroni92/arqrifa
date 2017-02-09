package org.arqrifa.controllers;

import java.util.Date;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.VMVerificacion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuario"})
public class ControladorUsuario extends Controlador {

    public void index_get() {
        mostrarVista(this.usuario == null ? "login.jsp" : this.usuario.getRol().toLowerCase() + "/index.jsp");
    }

    public void ingresar_get() {
        mostrarVista("login.jsp");
    }

    public void ingresar_post() {
        try {
            usuario = cliente.login(Integer.parseInt(request.getParameter("ci")), request.getParameter("pass"));
            if (usuario == null) {
                throw new Exception("Usuario y/o contraseña incorrectos.");
            }
            sesion.setAttribute("usuario", usuario);
            mostrarVista(usuario.getRol().toLowerCase() + "/index.jsp");
        } catch (Exception ex) {
            mostrarVista("login.jsp", new ViewModel(ex.getMessage()));
        }
    }

    public void salir_get() {
        ViewModel vm = new ViewModel();
        try {
            sesion.removeAttribute("usuario");
            mostrarVista("login.jsp");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("login.jsp");
    }

    public void registro_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje("Error al cargar las generaciones");
        }
        mostrarVista("registro.jsp", vm);

    }

    public void registro_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            if (vm.getCi().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getEmail().isEmpty() || vm.getContrasena().isEmpty() || vm.getGeneracion().isEmpty()) {
                throw new Exception("Completa todos los campos obligatorios.");
            }

            DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "", Integer.parseInt(vm.getGeneracion()), 0);
            cliente.agregarSolicitud(new DTSolicitud(0, new Date(), false, dtUsuario));
            mostrarVista("login.jsp", new ViewModel("Registro exitoso! <br>Se ha enviado un mail de verificación a tu correo electrónico."));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("registro.jsp", vm);
        }
    }

    public void verificar_get() {
        VMVerificacion vm;
        try {
            cliente.verificarSolicitud(Integer.parseInt(request.getParameter("codigo")));
            vm = new VMVerificacion(true, "Ahora solo debes esperar a que sea aprobada por el encargado.", "¡Solicitud verificada exitosamente!");
        } catch (Exception ex) {
            vm = new VMVerificacion(false, "Quizas la solicitud fue rechazada.", "Código de verificación incorrecto");
        }
        mostrarVista("verificar.jsp", vm);
    }

    public void ver_calendario_get() {
        VMListadoReuniones vm = new VMListadoReuniones();
        try {
            if (this.usuario.getRol().equals(DTUsuario.ADMIN)) {
                vm.setReuniones(cliente.listarReunionesTodas());
            } else {
                vm.setReuniones(cliente.listarReunionesPorGeneracion(this.usuario.getGeneracion()));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/calendario.jsp", vm);
    }

    public void ver_encuesta_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("reunionId")));

            if (reunion.getEncuesta() == null || reunion.getGeneracion() != this.usuario.getGeneracion()) {
                throw new Exception("Recurso no encontrado");
            }

            mostrarVista("encuestas/detalles.jsp", new VMReunion(reunion, ""));
        } catch (Exception e) {
            mostrarVista("error/404.jsp");
        }
    }

    public void perfil_get() {
        mostrarVista("usuarios/detalles.jsp", new VMUsuario(this.usuario, ""));
    }

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            DTUsuario dtUsuario = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));

            if (this.usuario.getRol().equals(DTUsuario.ADMIN) || this.usuario.getRol().equals(DTUsuario.ENCARGADO) && this.usuario.getGeneracion() == dtUsuario.getGeneracion()) {
                vm.setUsuario(dtUsuario);
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
    }
}
