package org.arqrifa.controllers;

import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.viewmodels.VMMantenimientoReunion;

@WebServlet(name = "ControladorPanel", urlPatterns = {"/panel"})
public class ControladorPanel extends Controlador {

    public void index_get() {

        VMMantenimientoReunion vm = new VMMantenimientoReunion();
        try {

            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

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
        mostrarVista("reuniones/panel.jsp", vm);
    }

    public void iniciar_post() {
        VMMantenimientoReunion vm = (VMMantenimientoReunion) cargarModelo(new VMMantenimientoReunion());
        try {
            cliente.iniciarReunion(cliente.buscarReunion(Integer.parseInt(vm.getId())));
            vm.setEstado(DTReunion.INICIADA);
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
                throw new Exception("Ingrese alguna resolución.");
            }

            DTReunion reunion = cliente.buscarReunion(Integer.parseInt(vm.getId()));
            reunion.setId(Integer.parseInt(vm.getId()));
            reunion.setObservaciones(vm.getObservaciones());
            reunion.setResoluciones(vm.getResoluciones());

            cliente.finalizarReunion(reunion);
            sesion.setAttribute("mensaje", "Reunión finalizada exitosamente.");
            response.sendRedirect("reunion?accion=detalles&id=" + vm.getId());
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("reuniones/panel.jsp", vm);
        }
    }
}
