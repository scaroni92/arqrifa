package org.arqrifa.servlets;

import java.util.ArrayList;
import java.util.List;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.rest.ClienteJersey;
import org.arqrifa.viewmodels.VMSolicitudes;

public class ControladorEncargados extends Controlador {

    public void solicitudes_get() {
        List<DTSolicitud> lista = new ArrayList();
        try {
            lista = new ClienteJersey().listarSolicitudes((DTUsuario) sesion.getAttribute("usuario"));
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, ""));
            sesion.setAttribute("solicitudes", lista);
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, e.getMessage()));
        }
    }

    public void confirmar_get() {
        List<DTSolicitud> solicitudes = new ArrayList();
        try {
            int ci = Integer.parseInt(request.getParameter("ci"));
            solicitudes = (List<DTSolicitud>) sesion.getAttribute("solicitudes");
            DTSolicitud solicitud = null;
            for (DTSolicitud s : solicitudes) {
                if (s.getCi() == ci) {
                    solicitud = s;
                }
            }
            
            if (solicitud == null) {
                throw new Exception("Error solicitud nula.");
            }
            
            new ClienteJersey().confirmarSolicitud(solicitud);
            
            solicitudes = new ClienteJersey().listarSolicitudes((DTUsuario)sesion.getAttribute("usuario"));
            sesion.setAttribute("solicitudes", solicitudes);
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(solicitudes, e.getMessage()));
        }
    }
}
