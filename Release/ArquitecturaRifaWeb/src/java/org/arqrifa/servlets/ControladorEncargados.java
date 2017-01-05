package org.arqrifa.servlets;

import java.util.ArrayList;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void ver_solicitudes_get() {
        try {
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(cliente.listarSolicitudes(usuario.getGeneracion())), ""));
        } catch (Exception e) {
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), "Error al listar las solicitudes"));
        }
    }

    public void confirmar_solicitud_get() {
        try {
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_solicitud_get() {
        try {
            cliente.rechazarSolicitud(cliente.buscarSolicitud(Integer.parseInt(request.getParameter("ci"))));
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud rechazada exitosamente."));
        }
        catch(NumberFormatException e){
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), "Ingrese una cédula válida."));
        }
        catch (Exception e) {
            mostrarVista("Encargado/ver_solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }
    }
}
