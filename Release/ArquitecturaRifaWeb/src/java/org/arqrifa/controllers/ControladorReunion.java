package org.arqrifa.controllers;

import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMListaAsistencias;
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
            reunion.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());
            reunion.setGeneracion(usuario.getGeneracion());

            cliente.agregarReunion(reunion);

            sesion.setAttribute("mensaje", "Reunión agendada exitosamente");
            response.sendRedirect("usuarios?accion=ver-calendario");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/agendar.jsp", vm);
        }
    }

    public void modificar_get() {
        VMMantenimientoReunion vm = new VMMantenimientoReunion();
        try {
            cargarReunionEnModelo(vm, cliente.buscarReunion(Integer.parseInt(request.getParameter("id"))));
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

            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getId()));

            reunion.setTitulo(vm.getTitulo());
            reunion.setDescripcion(vm.getDescripcion());
            reunion.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            reunion.setDuracion(Integer.parseInt(vm.getDuracion()));
            reunion.setObligatoria(vm.isObligatoria());
            reunion.setLugar(vm.getLugar());
            reunion.setTemas(vm.getTemas());

            cliente.modificarReunion(reunion);
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
            reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));
            cliente.eliminarReunion(reunion);

            sesion.setAttribute("mensaje", "Reunion eliminada exitosamente");
            response.sendRedirect("usuario?accion=ver-calendario");
        } catch (Exception e) {
            mostrarVista("Reunion/eliminar.jsp", new VMReunion(reunion, e.getMessage()));
        }
    }

    public void iniciar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {
            cliente.iniciarReunion(cliente.buscarReunion(Integer.parseInt(vm.getId())));
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

            cliente.finalizarReunion(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("Reuniones");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/panel.jsp", vm);
        }
    }

    private void cargarReunionEnModelo(VMMantenimientoReunion vm, DTReunion reunion) throws Exception {
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
    }

    public void detalles_get() {
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            if (this.usuario.getRol().equals(DTUsuario.ENCARGADO) && this.usuario.getGeneracion() != reunion.getGeneracion()) {
                mostrarVista("error/403.jsp");
            }

            mostrarVista("reuniones/detalles.jsp", new VMReunion(reunion, ""));

        } catch (Exception e) {
            mostrarVista("error/500.jsp");
        }
    }

    public void ver_lista_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            if (reunion.getGeneracion() == this.usuario.getGeneracion()) {
                vm.setReunion(reunion);
                vm.setAsistencias(cliente.listarAsistencias(reunion));
            }

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/lista.jsp", vm);
    }
    
    public void marcar_asistencia_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            DTUsuario estudiante = cliente.buscarUsuario(Integer.parseInt(request.getParameter("ci")));
            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            cliente.agregarAsistencia(reunion, estudiante);
            sesion.setAttribute("mensaje", "Asistenia marcada exitosamente.");     
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        this.ver_lista_get();
    }
}
