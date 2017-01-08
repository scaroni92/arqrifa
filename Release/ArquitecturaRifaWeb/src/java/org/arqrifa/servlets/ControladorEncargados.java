package org.arqrifa.servlets;

import java.util.ArrayList;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void ver_solicitudes_get() {
        try {
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(cliente.listarSolicitudes(getUsuario().getGeneracion())), ""));
        } catch (Exception e) {
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), "Error al listar las solicitudes"));
        }
    }

    public void confirmar_solicitud_get() {
        try {
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(getUsuario().getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_solicitud_get() {
        VMSolicitudes vm = new VMSolicitudes();
        try {
            cliente.rechazarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            vm = new VMSolicitudes(cliente.listarSolicitudes(getUsuario().getGeneracion()), "Solicitud rechazada exitosamente.");
        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese una cédula válida.");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encargado/ver_solicitudes.jsp", vm);
    }
}
