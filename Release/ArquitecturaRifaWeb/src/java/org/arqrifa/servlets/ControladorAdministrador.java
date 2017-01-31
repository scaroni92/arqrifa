package org.arqrifa.servlets;

import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.exceptions.ArquitecturaRifaException;
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
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            if (vm.getCi().isEmpty() || vm.getGeneracion().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty() || vm.getEmail().isEmpty()) {
                throw new ArquitecturaRifaException("Complete todos los campos obligatorios.");
            }

            DTUsuario encargado = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "Encargado", Integer.parseInt(vm.getGeneracion()), 0);
            cliente.agregarEncargado(encargado);
            vm = new VMUsuarioMantenimiento("Encargado agregado exitosamente.");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
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

            DTGeneracion generacion = new DTGeneracion(Integer.parseInt(request.getParameter("anio")));
            cliente.agregarGeneracion(generacion);

            vm.getGeneraciones().add(generacion);
            vm.setMensaje("Generación agregada exitosamente.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Admin/agregar_generacion.jsp", vm);
    }
}
