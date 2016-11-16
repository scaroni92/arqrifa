package org.arqrifa.servlets;

import org.arqrifa.datatypes.DTGeneracion;
import org.arqrifa.datatypes.DTUsuario;
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
        DTUsuario encargado = null;

        VMUsuario vm = (VMUsuario) cargarModelo(new VMUsuario());
        
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
            
            int ci;
            try {
                ci = Integer.parseInt(vm.getCi());
            } catch (NumberFormatException e) {
                throw new Exception("Ingrese una cédula válida.");
            }
            
            int generacion = Integer.parseInt(vm.getGeneracion());

            
            
            cliente.agregarEncargado(new DTUsuario(ci, vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), "encargado", generacion));

            
            
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

            int anio = Integer.parseInt(request.getParameter("anio"));
            DTGeneracion generacion = new DTGeneracion(anio);

            cliente.agregarGeneracion(generacion);

            vm.setMensaje("Generación agregada con éxito.");

        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese un año válido.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("/Vistas/Admin/agregarGeneracion.jsp", vm);
    }
}
