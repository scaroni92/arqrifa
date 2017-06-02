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

    DTReunion reunionActiva;

    public void index_get() {
        ViewModel vm = new ViewModel();
        try {
            reunionActiva = new RecursoReuniones().buscarActual(usuario.getGeneracion());
            if (reunionActiva == null) {
                throw new Exception("No hay reuniones para hoy");
            }
            if (reunionActiva.getEstado().equals(DTReunion.FINALIZADA)) {
                throw new Exception("No hay reuniones pendientes para hoy");
            }
            sesion.setAttribute("reunionActiva", reunionActiva);

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void iniciar_post() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");
            new RecursoReuniones().iniciar(reunion);
            reunion.setEstado(DTReunion.INICIADA);
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_post() {
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");

            // TODO:  comprobar resoluciones en lógica y en JS
            reunion.setObservaciones(request.getParameter("observaciones"));
            String[] resoluciones = request.getParameterValues("resoluciones");

            if (resoluciones == null) {
                throw new Exception("Ingrese alguna resolución");
            }

            reunion.setResoluciones(Arrays.asList(resoluciones));
            new RecursoReuniones().finalizar(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("reunion?accion=detalles&id=" + reunion.getId());
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void ver_lista_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            vm.setReunion(reunionActiva);
            vm.setAsistencias(new RecursoReuniones().listarAsistencias(reunionActiva.getId()));
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/lista.jsp", vm);
    }

    public void habilitar_lista_get() {
        ViewModel vm = new ViewModel();
        try {

            new RecursoReuniones().hablitarLista(reunionActiva);
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

            new RecursoReuniones().deshablitarLista(reunionActiva);
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
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");
            new RecursoEncuestas().iniciarVotacion(reunion);
            reunion.getEncuesta().setHabilitada(true);
            //reunion.setEstado(DTReunion.INICIADA);
            vm.setMensaje("Votación iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void finalizar_votacion_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");
            new RecursoEncuestas().finalizarVotacion(reunion);
            reunion.getEncuesta().setHabilitada(false);
            vm.setMensaje("Votación finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void marcar_asistencia_get() {
        VMListaAsistencias vm = new VMListaAsistencias();
        try {
            DTUsuario estudiante = new RecursoUsuarios().buscar(request.getParameter("ci"));
            new RecursoReuniones().agregarAsistencia(estudiante, reunionActiva);
            sesion.setAttribute("mensaje", "Asistenia marcada exitosamente.");
        } catch (Exception e) {
            sesion.setAttribute("mensaje", e.getMessage());

        }
        this.ver_lista_get();

    }
}
