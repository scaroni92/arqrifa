package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMUsuario;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuario"})
public class ControladorUsuario extends Controlador {

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
