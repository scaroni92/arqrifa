package org.arqrifa.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListadoUsuarios;
import org.arqrifa.viewmodels.VMMantenimientoReunion;
import org.arqrifa.viewmodels.VMReunion;

@WebServlet(name = "ControladorReunion", urlPatterns = {"/reunion"})
public class ControladorReunion extends Controlador {

    public void agendar_get() {
        mostrarVista("reuniones/agendar.jsp");
    }

    public void agendar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty() || vm.getFecha().isEmpty() || vm.getHora().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }
            if (vm.getTemas().isEmpty()) {
                throw new Exception("Agregue los temas a debatir");
            }

            DTReunion reunion = new DTReunion();

            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(formatearFecha(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());
            reunion.setGeneracion(usuario.getGeneracion());

            new RecursoReuniones().agregar(reunion);

            sesion.setAttribute("mensaje", "Reunión agendada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/agendar.jsp", vm);
        }
    }

    private static Date formatearFecha(String fecha) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fecha);
    }

    public void modificar_get() {
        VMMantenimientoReunion vm = new VMMantenimientoReunion();
        try {
            DTReunion reunion =  new RecursoReuniones().buscar(request.getParameter("id"));
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
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }

            if (vm.getTemas().isEmpty()) {
                throw new Exception("Ingrese los temas a debatir en la reunión.");
            }

            DTReunion reunion =  new RecursoReuniones().buscar(vm.getId());
            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(formatearFecha(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());

             new RecursoReuniones().modificar(reunion);
            sesion.setAttribute("mensaje", "Reunión modificada exitosamente");
            this.detalles_get();
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/modificar.jsp", vm);
        }

    }

    public void eliminar_post() {
        DTReunion reunion = null;
        try {
            reunion =  new RecursoReuniones().buscar(request.getParameter("id"));
             new RecursoReuniones().eliminar(reunion.getId());

            sesion.setAttribute("mensaje", "Reunion eliminada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            mostrarVista("Reunion/eliminar.jsp", new VMReunion(reunion, e.getMessage()));
        }
    }

    public void iniciar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {
             new RecursoReuniones().iniciar( new RecursoReuniones().buscar(vm.getId()));
            vm.setEstado("Iniciada");
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {

            if (vm.getResoluciones().isEmpty()) {
                throw new Exception("Ingrese alguna resolución de la reunión.");
            }

            DTReunion reunion = new DTReunion();
            reunion.setId(Integer.parseInt(vm.getId()));
            reunion.setObservaciones(vm.getObservaciones());
            reunion.setResoluciones(vm.getResoluciones());

             new RecursoReuniones().finalizar(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("Reuniones");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/panel.jsp", vm);
        }
    }

    public void detalles_get() {
        try {
            DTReunion reunion =  new RecursoReuniones().buscar(request.getParameter("id"));

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

    public void ver_participantes_get() {
        VMListadoUsuarios vm = new VMListadoUsuarios();
        try {
            DTReunion reunion =  new RecursoReuniones().buscar(request.getParameter("id"));
            vm.setUsuarios(reunion.getParticipantes());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("usuarios/listado.jsp", vm);
    }
}
