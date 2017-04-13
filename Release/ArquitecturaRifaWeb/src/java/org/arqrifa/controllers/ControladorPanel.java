package org.arqrifa.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.viewmodels.VMMantenimientoReunion;
import org.arqrifa.viewmodels.ViewModel;

@WebServlet(name = "ControladorPanel", urlPatterns = {"/panel"})
public class ControladorPanel extends Controlador {

    public void index_get() {
        try {
            DTReunion reunion = cliente.buscarReunionDelDia(usuario.getGeneracion());
            if (reunion == null) {
                throw new Exception("No hay reuniones para hoy");
            }
            if (reunion.getEstado().equals(DTReunion.FINALIZADA)) {
                throw new Exception("No hay reuniones pendientes para hoy");
            }
            sesion.setAttribute("reunionActiva", reunion);

            mostrarVista("reuniones/panel.jsp");
        } catch (Exception e) {
            mostrarVista("encargado/index.jsp", new ViewModel(e.getMessage()));
        }

    }

    public void iniciar_post() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = (DTReunion)sesion.getAttribute("reunionActiva");
            cliente.iniciarReunion(reunion);
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
            cliente.finalizarReunion(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("reunion?accion=detalles&id=" + reunion.getId());
        } catch (Exception e) {
            mostrarVista("reuniones/panel.jsp", new ViewModel(e.getMessage()));
        }
    }

    public void habilitar_lista_get() {
        ViewModel vm = new ViewModel();
        try {

            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");
            cliente.habilitarLista(reunion);
            reunion.setEstado(DTReunion.LISTADO);

            vm.setMensaje("Lista de asistencias habilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void deshabilitar_lista_get() {
        ViewModel vm = new ViewModel();
        try {

            DTReunion reunion = (DTReunion) sesion.getAttribute("reunionActiva");
            cliente.deshabilitarLista(reunion);
            reunion.setEstado(DTReunion.INICIADA);

            vm.setMensaje("Lista de asistencias deshabilitada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }
    
    public void iniciar_votacion_get() {
        ViewModel vm = new ViewModel();
        try {
            DTReunion reunion = (DTReunion)sesion.getAttribute("reunionActiva");
            cliente.iniciarVotacion(reunion);
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
            DTReunion reunion = (DTReunion)sesion.getAttribute("reunionActiva");
            cliente.finalizarVotacion(reunion);
            reunion.getEncuesta().setHabilitada(false);
            vm.setMensaje("Votación finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("reuniones/panel.jsp", vm);
    }
}
