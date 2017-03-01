package org.arqrifa.controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
            response.sendRedirect("");
        } catch (IOException e) {
            vm.setMensaje(e.getMessage());
        }

    }

    /* public void ver_calendario_get() {
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
    }*/
    public void ver_calendario_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        List<DTReunion> reuniones;
        try {
            if (usuario.getRol().equals(DTUsuario.ADMIN)) {
                reuniones = cliente.listarReunionesTodas();
            } else {
                reuniones = cliente.listarReunionesPorGeneracion(usuario.getGeneracion());
            }

            if (vm.getFiltro().equalsIgnoreCase(DTReunion.PENDIENTE) || vm.getFiltro().equalsIgnoreCase(DTReunion.FINALIZADA)) {
                reuniones = reuniones.stream().filter(reunion -> reunion.getEstado().equalsIgnoreCase(vm.getFiltro())).collect(Collectors.toList());
            }
            vm.setReuniones(reuniones);
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
