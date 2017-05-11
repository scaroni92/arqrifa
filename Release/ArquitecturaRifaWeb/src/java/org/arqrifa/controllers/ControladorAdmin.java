package org.arqrifa.controllers;

import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListadoReuniones;

import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMMantenimientoUsuario;

@WebServlet(name = "ControladorAdmin", urlPatterns = {"/admin"})
public class ControladorAdmin extends Controlador {

    public void buscar_usuario_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            if (request.getParameter("ci").isEmpty()) {
                vm.setUsuarios(cliente.listarUsuarios(0));
            } else {
                vm.getUsuarios().add(cliente.buscarUsuario(request.getParameter("ci")));
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void agregar_usuario_get() {

        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/agregar.jsp", vm);
    }

    public void agregar_usuario_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            if (vm.getCi().isEmpty() || vm.getGeneracion().isEmpty() || vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty() || vm.getEmail().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }

            DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), vm.getRol(), Integer.parseInt(vm.getGeneracion()), 0);
            cliente.agregarUsuario(dtUsuario);
            vm = new VMMantenimientoUsuario();
            vm.setMensaje("Usuario agregado exitosamente");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        mostrarVista("usuarios/agregar.jsp", vm);
    }

    public void modificar_usuario_get() {
        VMMantenimientoUsuario vm = new VMMantenimientoUsuario();
        try {
            DTUsuario dtUsuario = cliente.buscarUsuario(request.getParameter("ci"));
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

    public void modificar_usuario_post() {
        VMMantenimientoUsuario vm = (VMMantenimientoUsuario) cargarModelo(new VMMantenimientoUsuario());
        try {
            vm.setGeneraciones(cliente.listarGeneraciones());

            if (vm.getNombre().isEmpty() || vm.getApellido().isEmpty() || vm.getContrasena().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }

            DTUsuario dtUsuario = new DTUsuario(Integer.parseInt(vm.getCi()), vm.getNombre(), vm.getApellido(), vm.getContrasena(), vm.getEmail(), vm.getRol(), Integer.parseInt(vm.getGeneracion()), 0);
            cliente.modificarUsuario(dtUsuario);
            vm.setMensaje("Usuario modificado exitosamente");
        } catch (Exception ex) {
            vm.setMensaje(ex.getMessage());
        }
        mostrarVista("usuarios/modificar.jsp", vm);
    }

    public void listar_usuarios_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            vm.setUsuarios(cliente.listarUsuarios(0));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    public void listar_reuniones_get() {
        VMListadoReuniones vm = new VMListadoReuniones();
        try {
            vm.setReuniones(cliente.listarReuniones(0));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/listado.jsp", vm);
    }
}
