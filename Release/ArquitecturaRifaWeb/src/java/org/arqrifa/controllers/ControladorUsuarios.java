package org.arqrifa.controllers;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoGeneraciones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;
import org.arqrifa.viewmodels.VMUsuario;
//Acceso: admin y encargado
//Permisos: encargado solo lectura

@WebServlet(name = "ControladorUsuarios", urlPatterns = {"/usuarios"})
public class ControladorUsuarios extends Controlador {

    private final RecursoUsuarios recurso = new RecursoUsuarios();

    public void index_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            vm.setUsuarios(recurso.listar(usuario.getGeneracion()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void agregar_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            if (isNotAdmin()) {
                return;
            }
            vm.setGeneraciones(new RecursoGeneraciones().listar());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/agregar.jsp", vm);
    }

    private boolean isNotAdmin() throws IOException {
        if (!usuario.getRol().equals(DTUsuario.ADMIN)) {
            response.sendError(403);
            return true;
        }
        return false;
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
            if (isNotAdmin()) {
                return;
            }
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

    public void buscar_get() {
        String ci = request.getParameter("ci");
        VMListadoUsuarios vm = new VMListadoUsuarios();
        DTUsuario dtu;
        
        try {

            if (ci.isEmpty()) {
                vm.setUsuarios(recurso.listar(usuario.getGeneracion()));
            } else {
                dtu = recurso.buscar(ci);
                if (usuario.getGeneracion() == dtu.getGeneracion()) {
                    vm.getUsuarios().add(dtu);
                }
            }
            
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void detalles_get() {
        VMUsuario vm = new VMUsuario();
        try {
            DTUsuario dtUsuario = recurso.buscar(request.getParameter("ci"));

            if (usuario.getRol().equals(DTUsuario.ENCARGADO) && usuario.getGeneracion() != dtUsuario.getGeneracion()) {
                response.sendError(403);
                return;
            }

            vm.setUsuario(dtUsuario);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/detalles.jsp", vm);
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
