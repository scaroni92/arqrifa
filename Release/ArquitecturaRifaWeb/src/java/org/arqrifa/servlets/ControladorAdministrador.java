package org.arqrifa.servlets;

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

        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            int ci = Validador.validarCi(request.getParameter("ci"));
            int generacion = Integer.parseInt(vm.getGeneracion());
            if (vm.getNombre().isEmpty()) {
                throw new Exception("El campo nombre no puede estar vacio");
            }
            if (vm.getApellido().isEmpty()) {
                throw new Exception("El campo apellido no puede estar vacio");
            }
            if (vm.getContrasena().isEmpty()) {
                throw new Exception("El campo contraseña no puede estar vacio");
            }
            if (vm.getEmail().isEmpty()) {
                throw new Exception("El campo email no puede estar vacio");
            }

            cliente.agregarEncargado(new DTUsuario(ci, vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "Encargado", generacion));

            vm.setMensaje("Encargado agregado exitosamente.");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
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
        VMGeneraciones vm = (VMGeneraciones) request.getAttribute("modelo");

        try {

            DTGeneracion generacion = new DTGeneracion(Integer.parseInt(request.getParameter("anio")));

            cliente.agregarGeneracion(generacion);

            vm.setMensaje("Generación agregada exitosamente.");

        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese un año válido.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }
}
