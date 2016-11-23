package org.arqrifa.servlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTSolicitud;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.validador.Validador;
import org.arqrifa.viewmodels.VMReunionMantenimiento;
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
            cliente.confirmarSolicitud(cliente.buscarSolicitud(Validador.validarCi(request.getParameter("ci"))));

            DTUsuario usuario = (DTUsuario) sesion.getAttribute("usuario");
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(cliente.listarSolicitudes(usuario.getGeneracion()), "Solicitud confirmada exitosamente."));
        } catch (Exception e) {
            mostrarVista("Vistas/Encargado/solicitudes.jsp", new VMSolicitudes(new ArrayList(), e.getMessage()));
        }

    }

    public void rechazar_get() {
        try {
            cliente.rechazarSolicitud(cliente.buscarSolicitud(Validador.validarCi(request.getParameter("ci"))));

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
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());

        try {
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty()) {
                throw new Exception("Debe completar todos los campos.");
            }
            if (vm.getTemas().isEmpty()) {
                throw new Exception("Ingrese los temas de se debatiran en la reunión.");
            }

            int duracion = Integer.parseInt(vm.getDuracion());

            Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora());

            DTReunion r = new DTReunion();

            r.setGeneracion(((DTUsuario) sesion.getAttribute("usuario")).getGeneracion());
            r.setTitulo(vm.getTitulo());
            r.setDescripcion(vm.getDescripcion());
            r.setFecha(fecha);
            r.setDuracion(duracion);
            r.setObligatoria(vm.isObligatoria());
            r.setLugar(vm.getLugar());
            
            String temas = request.getParameter("temas");
            
            StringTokenizer st = new StringTokenizer(temas, "\n", false);
            while (st.hasMoreTokens()) {
                r.getTemas().add(st.nextToken());
            }

            cliente.agendarReunion(r);
            vm = new VMReunionMantenimiento();
            vm.setMensaje("Reuníon agendada exitosamente.");

        } catch (NumberFormatException e) {
            vm.setMensaje("Ingrese una duración válida.");
        } catch (ParseException e) {
            vm.setMensaje("Ingrese una fecha válida");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }

        mostrarVista("Vistas/Encargado/agendar.jsp", vm);
    }
}
