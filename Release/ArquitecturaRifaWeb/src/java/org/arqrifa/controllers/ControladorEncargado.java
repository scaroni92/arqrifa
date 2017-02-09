package org.arqrifa.controllers;
//Acceso: ENCARGADO

import javax.servlet.annotation.WebServlet;
import org.arqrifa.viewmodels.VMListadoReuniones;
import org.arqrifa.viewmodels.VMListadoSolicitudes;
import org.arqrifa.viewmodels.VMListadoUsuarios;

@WebServlet(name = "ControladorEncargado", urlPatterns = {"/encargado"})
public class ControladorEncargado extends Controlador {

    public void listar_estudiantes_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            vm.setUsuarios(cliente.listarEstudiantesPorGeneracion(this.usuario.getGeneracion()));
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
        VMListadoReuniones vm = new VMListadoReuniones();
        try {
            vm.setReuniones(cliente.listarReunionesPorGeneracion(this.usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/listado.jsp", vm);
    }
}
