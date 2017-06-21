package org.arqrifa.controllers;

import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
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
            reunionActiva = (DTReunion)sesion.getAttribute("reunionActiva");
            if (reunionActiva == null) {
                response.sendRedirect("index");
                return;
            }
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void iniciar_reunion_post() {
        ViewModel vm = new ViewModel();
        try {
            recurso.iniciar(reunionActiva);
            reunionActiva.setEstado(DTReunion.ESTADO_INICIADA);
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_reunion_post() {
        try {
            reunionActiva.setObservaciones(request.getParameter("observaciones"));
            reunionActiva.setResoluciones(Arrays.asList(request.getParameterValues("resoluciones")));
            recurso.finalizar(reunionActiva);
            sesion.removeAttribute("reunionActiva");
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("reuniones?accion=detalles&id=" + reunionActiva.getId());
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void lista_post() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            vm.setReunion(reunionActiva);
            vm.setAsistencias(recurso.listarAsistencias(reunionActiva.getId()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/lista.jsp", vm);
    }

    public void habilitar_lista_post() {
        ViewModel vm = new ViewModel();
        try {

            recurso.hablitarLista(reunionActiva);
            reunionActiva.setEstado(DTReunion.ESTADO_LISTADO);

            vm.setMensaje("Lista de asistencias habilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void deshabilitar_lista_post() {
        ViewModel vm = new ViewModel();
        try {
            recurso.deshablitarLista(reunionActiva);
            reunionActiva.setEstado(DTReunion.ESTADO_INICIADA);
            vm.setMensaje("Lista de asistencias deshabilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void habilitar_votacion_post() {
        ViewModel vm = new ViewModel();
        try {
            new RecursoReuniones().habilitarVotacion(reunionActiva);
            reunionActiva.setEstado(DTReunion.ESTADO_VOTACION);
            reunionActiva.setEstado("Votacion");
            vm.setMensaje("Encuesta habilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void deshabilitar_votacion_post() {
        ViewModel vm = new ViewModel();
        try {
            new RecursoReuniones().deshabilitarVotacion(reunionActiva);
            reunionActiva.setEstado(DTReunion.ESTADO_INICIADA);
            vm.setMensaje("Encuesta deshabilitada exitosamente");
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
        lista_post();
    }
    
}
