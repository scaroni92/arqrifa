package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMListadoSolicitudes;
import org.arqrifa.viewmodels.VMListadoUsuarios;

@WebServlet(name = "ControladorEncargado", urlPatterns = {"/encargado"})
public class ControladorEncargado extends Controlador {

    public void listar_estudiantes_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            vm.setUsuarios(cliente.listarUsuarios(usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void listar_solicitudes_get() {
        VMListadoSolicitudes vm = new VMListadoSolicitudes();
        try {
            vm.setSolicitudes(cliente.listarSolicitudes(this.usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/solicitudes.jsp", vm);
    }

    public void listar_reuniones_get() {
        VMListadoReuniones vm = (VMListadoReuniones) cargarModelo(new VMListadoReuniones());
        try {
            vm.setReuniones(cliente.listarReuniones(usuario.getGeneracion()));

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/listado.jsp", vm);
    }

    public void buscar_usuario_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            //TODO hacer busqueda con criterio

            if (request.getParameter("ci").isEmpty()) {
                vm.setUsuarios(cliente.listarUsuarios(usuario.getGeneracion()));
            } else {
                DTUsuario estudiante = cliente.buscarUsuario(request.getParameter("ci"));
                if (estudiante.getRol().equals(DTUsuario.ESTUDIANTE) && estudiante.getGeneracion() == usuario.getGeneracion()) {
                    vm.getUsuarios().add(estudiante);
                }
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }
}
