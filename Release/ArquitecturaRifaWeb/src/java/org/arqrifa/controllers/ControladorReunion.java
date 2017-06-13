package org.arqrifa.controllers;

import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.util.FormatoFecha;
import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMMantenimientoReunion;
import org.arqrifa.viewmodels.VMReunion;

//Acceso: Encargado
@WebServlet(name = "ControladorReunion", urlPatterns = {"/reunion"})
public class ControladorReunion extends Controlador {

    private final RecursoReuniones recurso = new RecursoReuniones();

    public void agendar_get() {
        mostrarVista("reuniones/agendar.jsp", new VMMantenimientoReunion());
    }

    public void agendar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {
            validarViewModel(vm);

            DTReunion reunion = new DTReunion();
            reunion.setId(vm.getId().isEmpty() ? 0 : Integer.parseInt(vm.getId()));
            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(FormatoFecha.convertirFecha(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());
            reunion.setGeneracion(usuario.getGeneracion());

            recurso.agregar(reunion);

            sesion.setAttribute("mensaje", "Reunión agendada exitosamente");
            response.sendRedirect("reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/agendar.jsp", vm);
        }
    }

    public void modificar_get() {
        VMMantenimientoReunion vm = new VMMantenimientoReunion();
        try {
            DTReunion reunion = new RecursoReuniones().buscar(request.getParameter("id"));
            vm.setId(String.valueOf(reunion.getId()));
            vm.setTitulo(reunion.getTitulo());
            vm.setDescripcion(reunion.getDescripcion());
            vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(reunion.getFecha()));
            vm.setHora(new SimpleDateFormat("HH:mm").format(reunion.getFecha()));
            vm.setDuracion(String.valueOf(reunion.getDuracion()));
            vm.setObligatoria(reunion.isObligatoria());
            vm.setLugar(reunion.getLugar());
            vm.setEstado(reunion.getEstado());
            vm.setTemas(reunion.getTemas());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/modificar.jsp", vm);
    }

    public void modificar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {

            validarViewModel(vm);

            DTReunion reunion = new RecursoReuniones().buscar(vm.getId());
            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(FormatoFecha.convertirFecha(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());

            recurso.modificar(reunion);

            sesion.setAttribute("mensaje", "Reunión modificada exitosamente");
            response.sendRedirect("reuniones");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/modificar.jsp", vm);
        }

    }

    public void eliminar_post() {
        DTReunion reunion = null;
        try {
            reunion = recurso.buscar(request.getParameter("id"));
            //Comprobar estado
            recurso.eliminar(reunion.getId());
            sesion.setAttribute("mensaje", "Reunion eliminada exitosamente");
            response.sendRedirect("reuniones");
        } catch (Exception e) {
            mostrarVista("reuniones/detalles.jsp", new VMReunion(reunion, e.getMessage()));
        }
    }

    public void index_get() {
        try {
            DTReunion reunion = recurso.buscar(request.getParameter("id"));

            if (reunion == null) {
                throw new Exception("");
            }

            if (this.usuario.getRol().equals(DTUsuario.ENCARGADO) && this.usuario.getGeneracion() != reunion.getGeneracion()) {
                mostrarVista("error/403.jsp");
            }
//TODO
            mostrarVista("reuniones/detalles.jsp", new VMReunion(reunion, ""));

        } catch (Exception e) {
            mostrarVista("error/404.jsp");
        }
    }

    //a reuniones
    public void ver_participantes_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            DTReunion reunion = recurso.buscar(request.getParameter("id"));
            vm.setUsuarios(reunion.getParticipantes());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }

    private void validarViewModel(VMMantenimientoReunion vm) throws Exception {
        if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty() || vm.getFecha().isEmpty() || vm.getHora().isEmpty()) {
            throw new Exception("Complete todos los campos obligatorios.");
        }
        if (vm.getTemas().isEmpty()) {
            throw new Exception("Agregue los temas a debatir");
        }
    }

}
