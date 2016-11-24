package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.validador.Validador;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void solicitudes_get() {
        try {
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            List<DTSolicitud> lista = new ArrayList(cliente.listarSolicitudes(usuario.getGeneracion()));

            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, ""));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), "Error al listar las solicitudes"));
        }
    }

    public void confirmar_solicitud_get() {
        try {
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Validador.validarCi(request.getParameter("ci"))));

            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_solicitud_get() {
        try {
            cliente.rechazarSolicitud(cliente.buscarSolicitud(Validador.validarCi(request.getParameter("ci"))));

            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud rechazada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }
    }
}
