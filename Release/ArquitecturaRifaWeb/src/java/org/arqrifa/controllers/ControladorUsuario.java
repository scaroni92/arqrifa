package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoGeneraciones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/usuario"})
public class ControladorUsuario extends Controlador {

    private final RecursoUsuarios recurso = new RecursoUsuarios();

    public void agregar_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            vm.setGeneraciones(new RecursoGeneraciones().listar());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/agregar.jsp", vm);
    }

    public void agregar_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            recurso.agregar(getUserFromViewModel(vm));
            sesion.setAttribute("mensaje", "Usuario agregado exitosamente");
            response.sendRedirect("usuarios");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
            mostrarVista("usuarios/agregar.jsp", vm);
        }

    }

    public void modificar_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            DTUsuario dtUsuario = recurso.buscar(request.getParameter("ci"));
            vm.setCi(String.valueOf(dtUsuario.getCi()));
            vm.setNombre(dtUsuario.getNombre());
            vm.setApellido(dtUsuario.getApellido());
            vm.setEmail(dtUsuario.getEmail());
            vm.setContrasena(dtUsuario.getContrasena());
            vm.setRol(dtUsuario.getRol());
            vm.setGeneracion(String.valueOf(dtUsuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/modificar.jsp", vm);
    }

    public void modificar_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {

            recurso.modificar(getUserFromViewModel(vm));

            sesion.setAttribute("mensaje", "Usuario modificado exitosamente");
            response.sendRedirect("usuarios");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
            mostrarVista("usuarios/modificar.jsp", vm);
        }
    }

    private DTUsuario getUserFromViewModel(VMMantenimientoUsuario vm) throws NumberFormatException, Exception {
        vm.setGeneraciones(new RecursoGeneraciones().listar());
        validarViewModel(vm);
        DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), vm.getRol(), Integer.parseInt(vm.getGeneracion()), 0);
        return dtUsuario;
    }

    private void validarViewModel(VMMantenimientoUsuario vm) throws Exception {
        if (vm.getCi().isEmpty() || vm.getGeneracion().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty() || vm.getEmail().isEmpty()) {
            throw new Exception("Complete todos los campos obligatorios.");
        }
    }
}
