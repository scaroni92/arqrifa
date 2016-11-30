package org.arqrifa.servlets;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMGeneraciones;
import org.arqrifa.viewmodels.VMUsuarioMantenimiento;

public class ControladorAdministrador extends Controlador {

    public void agregar_encargado_get() {
        VMUsuarioMantenimiento vm = new VMUsuarioMantenimiento();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Admin/agregar_encargado.jsp", vm);
    }

    public void agregar_encargado_post() {
        VMUsuarioMantenimiento vm = (VMUsuarioMantenimiento) cargarModelo(new VMUsuarioMantenimiento());
        List<DTGeneracion> generaciones = null;
        try {
            generaciones = cliente.listarGeneraciones();

            if (vm.getCi().isEmpty() || vm.getGeneracion().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty() || vm.getEmail().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }

            cliente.agregarEncargado(new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "Encargado", Integer.parseInt(vm.getGeneracion())));
            vm = new VMUsuarioMantenimiento("Encargado agregado exitosamente.");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        vm.setGeneraciones(generaciones);
        mostrarVista("Admin/agregar_encargado.jsp", vm);
    }

    public void agregar_generacion_get() {
        VMGeneraciones vm = new VMGeneraciones();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Admin/agregar_generacion.jsp", vm);
    }

    public void agregar_generacion_post() {
        VMGeneraciones vm = (VMGeneraciones) cargarModelo(new VMGeneraciones());
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
            cliente.agregarGeneracion(new DTGeneracion(Integer.parseInt(request.getParameter("anio"))));

            vm.setMensaje("Generación agregada exitosamente.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Admin/agregar_generacion.jsp", vm);
    }
}
