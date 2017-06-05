package org.arqrifa.controllers;

import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.RecursoEncuestas;
import org.arqrifa.rest.RecursoReuniones;
import org.arqrifa.rest.RecursoUsuarios;
import org.arqrifa.viewmodels.VMListaAsistencias;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorPanel", urlPatterns = {"/panel"})
public class ControladorPanel extends Controlador {

    private final RecursoReuniones recurso = new RecursoReuniones();
    DTReunion reunionActiva;

    public void index_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = recurso.buscarActual(usuario.getGeneracion());

            if (reunion == null) {
                sesion.setAttribute("mensaje", "No hay reuniones para hoy");
                response.sendRedirect("index");
                return;
            }
            reunionActiva = reunion;
            sesion.setAttribute("reunionActiva", reunionActiva);
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void iniciar_post() {
        ViewModel vm = new ViewModel();
        try {
            recurso.iniciar(reunionActiva);
            reunionActiva.setEstado(DTReunion.INICIADA);
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_post() {
        try {
            reunionActiva.setObservaciones(request.getParameter("observaciones"));
            reunionActiva.setResoluciones(Arrays.asList(request.getParameterValues("resoluciones")));
            recurso.finalizar(reunionActiva);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("reuniones?accion=detalles&id=" + reunionActiva.getId());
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void lista_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            vm.setReunion(reunionActiva);
            vm.setAsistencias(recurso.listarAsistencias(reunionActiva.getId()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/lista.jsp", vm);
    }

    public void habilitar_lista_get() {
        ViewModel vm = new ViewModel();
        try {

            recurso.hablitarLista(reunionActiva);
            reunionActiva.setEstado(DTReunion.LISTADO);

            vm.setMensaje("Lista de asistencias habilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void deshabilitar_lista_get() {
        ViewModel vm = new ViewModel();
        try {
            recurso.deshablitarLista(reunionActiva);
            reunionActiva.setEstado(DTReunion.INICIADA);
            vm.setMensaje("Lista de asistencias deshabilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void iniciar_votacion_get() {
        ViewModel vm = new ViewModel();
        try {
            new RecursoEncuestas().iniciarVotacion(reunionActiva);
            reunionActiva.getEncuesta().setHabilitada(true);
            //reunion.setEstado(DTReunion.VOTACION);
            vm.setMensaje("Votación iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_votacion_get() {
        ViewModel vm = new ViewModel();
        try {
            new RecursoEncuestas().finalizarVotacion(reunionActiva);
            reunionActiva.getEncuesta().setHabilitada(false);
            vm.setMensaje("Votación finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void marcar_asistencia_get() {
        try {
            DTUsuario estudiante = new RecursoUsuarios().buscar(request.getParameter("ci"));
            recurso.agregarAsistencia(estudiante, reunionActiva);
            sesion.setAttribute("mensaje", "Asistenia marcada exitosamente.");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());
        }
        lista_get();
    }
}
