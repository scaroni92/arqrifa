package org.arqrifa.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMUsuario;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuario"})
public class ControladorUsuario extends Controlador {

    public void ver_calendario_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        List<DTReunion> reuniones;
        try {
            if (usuario.getRol().equals(DTUsuario.ADMIN)) {
                reuniones = new RecursoReuniones().listar(0);
            } else {
                reuniones = new RecursoReuniones().listar(usuario.getGeneracion());
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

    public void perfil_get() {
        mostrarVista("usuarios/detalles.jsp", new VMUsuario(this.usuario, ""));
    }

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            DTUsuario dtUsuario = new RecursoUsuarios().buscar(request.getParameter("ci"));

            if (this.usuario.getRol().equals(DTUsuario.ADMIN) || this.usuario.getRol().equals(DTUsuario.ENCARGADO) && this.usuario.getGeneracion() == dtUsuario.getGeneracion()) {
                vm.setUsuario(dtUsuario);
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
    }
}
