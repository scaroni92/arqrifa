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
        try {
            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            List<DTSolicitud> lista = new ArrayList(cliente.listarSolicitudes(usuario.getGeneracion()));

            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(lista, ""));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), "Error al listar las solicitudes"));
        }
    }

    public void confirmar_get() {
        try {

            int ci;
            try {
                ci = Integer.parseInt(request.getParameter("ci"));
            } catch (Exception e) {
                throw new Exception("La cédula debe ser numérica.");
            }

            cliente.confirmarSolicitud(cliente.buscarSolicitud(ci));

            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_get() {
        try {
            int ci;
            try {
                ci = Integer.parseInt(request.getParameter("ci"));
            } catch (Exception e) {
                throw new Exception("La cédula debe ser numérica.");
            }
            
            cliente.rechazarSolicitud(cliente.buscarSolicitud(ci));

            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud rechazada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }
    }

    public void agendar_get() {
        mostrarVista("Vistas/Encargado/agendar.jsp");
    }

    public void agendar_post() {
        VMReunion vm = (VMReunion) cargarModelo(new VMReunion());

        try {

            if (vm.getTitulo().isEmpty()) {
                throw new Exception("Ingrese el título de la reunión.");
            }

            if (vm.getDescripcion().isEmpty()) {
                throw new Exception("Ingrese alguna descripción de la reunión.");
            }

            if (vm.getLugar().isEmpty()) {
                throw new Exception("Ingrese el lugar donde se realizará la reunión.");
            }

            DTUsuario u = (DTUsuario) sesion.getAttribute("usuario");
            Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora());
            cliente.agendarReunion(new DTReunion(0, vm.getTitulo(), vm.getDescripcion(), "", fecha, vm.isObligatoria(), u.getGeneracion(), "", vm.getLugar()));
            
            vm = new VMReunion();
            vm.setMensaje("Reuníon agendada exitosamente.");

        } catch (ParseException e) {
            vm.setMensaje("ingrese una fecha válida");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }

        mostrarVista("Vistas/Encargado/agendar.jsp", vm);
    }
}
