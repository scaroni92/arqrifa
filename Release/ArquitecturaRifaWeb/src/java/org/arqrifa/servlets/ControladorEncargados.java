package org.arqrifa.servlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.viewmodels.VMReunion;
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
            solicitudes = (ArrayList) sesion.getAttribute("solicitudes");
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
            solicitudes = (ArrayList) sesion.getAttribute("solicitudes");
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

    public void agendar_get() {
        mostrarVista("Vistas/Encargado/agendar.jsp");
    }

    public void agendar_post() {
        VMReunion vm = new VMReunion();

        try {
            DTUsuario u = (DTUsuario) sesion.getAttribute("usuario");

            vm.setTitulo(request.getParameter("titulo"));
            vm.setDescripcion(request.getParameter("descripcion"));
            vm.setFecha(request.getParameter("fecha"));
            vm.setHora(request.getParameter("hora"));
            vm.setObligatoria(request.getParameter("obligatoria") != null);
            vm.setLugar(request.getParameter("lugar"));

            Date fecha = new SimpleDateFormat("yyyy-mm-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora());
            

            cliente.agendarReunion(new DTReunion(0, vm.getTitulo(), vm.getDescripcion(), "", fecha, vm.isObligatoria(), u.getGeneracion(), "", vm.getLugar()));
            vm.setMensaje("reunion agendada");
        } catch (ParseException e) {
            vm.setMensaje("ingrese una fecha válida");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Vistas/Encargado/agendar.jsp", vm);

    }
}
