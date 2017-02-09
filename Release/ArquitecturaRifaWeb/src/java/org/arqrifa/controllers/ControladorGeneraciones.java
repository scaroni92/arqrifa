package org.arqrifa.controllers;
//acceso: ADMIN

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.viewmodels.VMListadoGeneraciones;
import org.arqrifa.viewmodels.VMListadoUsuarios;

@WebServlet(name = "ControladorGeneraciones", urlPatterns = {"/generaciones"})
public class ControladorGeneraciones extends Controlador {

    public void index_get() {
        VMListadoGeneraciones vm = new VMListadoGeneraciones();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("generaciones/index.jsp", vm);
    }

    public void agregar_post() {
        VMListadoGeneraciones vm = new VMListadoGeneraciones();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            DTGeneracion generacion = new DTGeneracion(Integer.parseInt(request.getParameter("anio")));
            cliente.agregarGeneracion(generacion);
            
            vm.setGeneraciones(cliente.listarGeneraciones());
            vm.setMensaje("Generación agregada exitosamente.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("generaciones/index.jsp", vm);
    }

    public void listar_estudiantes_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            vm.setUsuarios(cliente.listarEstudiantesPorGeneracion(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

}
