package org.arqrifa.servlets;

import java.util.List;
import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.validador.Validador;
import org.arqrifa.viewmodels.VMGeneraciones;
import org.arqrifa.viewmodels.VMUsuario;

public class ControladorAdministrador extends Controlador {

    public void agregar_encargado_get() {
        VMUsuario vm = new VMUsuario();

        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Admin/agregarEncargado.jsp", vm);
    }

    public void agregar_encargado_post() {
        VMUsuario vm = (VMUsuario) cargarModelo(new VMUsuario());
        List<DTGeneracion> generaciones = null;
        try {
            generaciones = cliente.listarGeneraciones();

            int ci = Validador.validarCi(request.getParameter("ci"));
            int generacion = Integer.parseInt(vm.getGeneracion());
            if (vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty() || vm.getEmail().isEmpty()) {
                throw new Exception("Todos los campos son obligatorios");
            }

            cliente.agregarEncargado(new DTUsuario(ci, vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "Encargado", generacion));

            vm = new VMUsuario();
            vm.setMensaje("Encargado agregado exitosamente.");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        vm.setGeneraciones(generaciones);
        mostrarVista("/Vistas/Admin/agregarEncargado.jsp", vm);
    }

    public void agregar_generacion_get() {
        VMGeneraciones vm = new VMGeneraciones();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }

    public void agregar_generacion_post() {
        VMGeneraciones vm = (VMGeneraciones) cargarModelo(new VMGeneraciones());

        try {

            cliente.agregarGeneracion(new DTGeneracion(Integer.parseInt(request.getParameter("anio"))));
            vm.setGeneraciones(cliente.listarGeneraciones());
            vm.setMensaje("Generación agregada exitosamente.");

        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese un año válido.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }
}
