package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void solicitudes_get() {
        List<DTSolicitud> lista = new ArrayList();
        try {

            lista = new ArrayList(cliente.listarSolicitudes((DTUsuario) sesion.getAttribute("usuario")));

            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, ""));
            sesion.setAttribute("solicitudes", lista);
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, e.getMessage()));
        }
    }

    public void confirmar_get() {
        List<DTSolicitud> solicitudes = new ArrayList();
        try {
            solicitudes = (ArrayList)sesion.getAttribute("solicitudes");
            DTSolicitud solicitud = null;
            for (DTSolicitud s : solicitudes) {
                if (s.getCi() == Integer.parseInt(request.getParameter("ci"))) {
                    solicitud = s;
                }
            }

            cliente.confirmarSolicitud(solicitud);
            solicitudes.remove(solicitud);

            sesion.setAttribute("solicitudes", solicitudes);
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, e.getMessage()));
        }
    }

    public void rechazar_get() {
        List<DTSolicitud> solicitudes = new ArrayList();
        try {
            solicitudes = (ArrayList)sesion.getAttribute("solicitudes");
            DTSolicitud solicitud = null;
            for (DTSolicitud s : solicitudes) {
                if (s.getCi() == Integer.parseInt(request.getParameter("ci"))) {
                    solicitud = s;
                }
            }

            cliente.rechazarSolicitud(solicitud);
            solicitudes.remove(solicitud);

            sesion.setAttribute("solicitudes", solicitudes);
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, "Solicitud rechazada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, e.getMessage()));
        }
    }
}
