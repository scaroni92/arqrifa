package org.arqrifa.servlets;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.arqrifa.datatypes.DTReunion;
import org.arqrifa.datatypes.DTUsuario;
import org.arqrifa.validador.Validador;
import org.arqrifa.viewmodels.VMReunion;
import org.arqrifa.viewmodels.VMReunionMantenimiento;

public class ControladorReuniones extends Controlador {

    public void ver_get() {
        VMReunion vm = new VMReunion();
        try {

            DTReunion r = cliente.buscarReunion(Validador.validarId(request.getParameter("id")));

            if (r.getGeneracion() != ((DTUsuario) sesion.getAttribute("usuario")).getGeneracion()) {
                mostrarVista("Error/no_encontrado.jsp");
            }

            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

            vm.setId(String.valueOf(r.getId()));
            vm.setGeneracion(String.valueOf(r.getGeneracion()));
            vm.setTitulo(r.getTitulo());
            vm.setDescripcion(r.getDescripcion());
            vm.setFecha(sdfFecha.format(r.getFecha()));
            vm.setHora(sdfHora.format(r.getFecha()));
            vm.setObligatoria(r.isObligatoria());
            vm.setObservaciones(r.getObservaciones());
            vm.setEstado(r.getEstado());
            vm.setLugar(r.getLugar());
            vm.setTemas(r.getTemas());
            vm.setResoluciones(r.getResoluciones());

            // Si la reunión está en condiciones se habilita el panel
            if ((r.getEstado().equals("Pendiente") && vm.getFecha().equals(sdfFecha.format(new Date()))) || r.getEstado().equals("Iniciada")) {
                vm.setHabilitarBotonPanel(r.getFecha().after(new Date()));
            }
            mostrarVista("Reunion/ver.jsp", vm);
        } catch (NullPointerException e) {
            mostrarVista("Error/no_encontrado.jsp");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
            mostrarVista("Reunion/ver.jsp", vm);
        }

    }

    public void agendar_get() {
        mostrarVista("Encargado/agendar_reunion.jsp");
    }

    public void agendar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());

        try {
            if (vm.getTitulo().isEmpty() || vm.getDescripcion().isEmpty() || vm.getLugar().isEmpty()) {
                throw new Exception("Complete todos los campos obligatorios.");
            }
            if (vm.getTemas().isEmpty()) {
                throw new Exception("Ingrese los temas a debatir en la reunión.");
            }

            DTReunion r = new DTReunion();
            r.setGeneracion(((DTUsuario) sesion.getAttribute("usuario")).getGeneracion());
            r.setTitulo(vm.getTitulo());
            r.setDescripcion(vm.getDescripcion());
            r.setFecha(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(vm.getFecha() + " " + vm.getHora()));
            r.setDuracion(Integer.parseInt(vm.getDuracion()));
            r.setObligatoria(vm.isObligatoria());
            r.setLugar(vm.getLugar());
            r.setTemas(Arrays.asList(vm.getTemas().split("\n")));

            cliente.agendarReunion(r);
            vm = new VMReunionMantenimiento();
            vm.setMensaje("Reuníon agendada exitosamente.");

        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }

        mostrarVista("Encargado/agendar_reunion.jsp", vm);
    }

    public void panel_post() {
        VMReunionMantenimiento vm = new VMReunionMantenimiento();
        try {
            DTReunion r = cliente.buscarReunion(Integer.parseInt(request.getParameter("id")));

            vm.setId(String.valueOf(r.getId()));
            vm.setTitulo(r.getTitulo());
            vm.setDescripcion(r.getDescripcion());
            vm.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(r.getFecha()));
            vm.setHora(new SimpleDateFormat("HH:mm").format(r.getFecha()));
            vm.setDuracion(String.valueOf(r.getDuracion()));
            vm.setEstado(r.getEstado());
            // cargar todos los atributos de ser necesario
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encargado/panel.jsp", vm);
    }

    public void iniciar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            cliente.iniciarReunion(cliente.buscarReunion(Integer.parseInt(request.getParameter("id"))));
            vm.setEstado("Iniciada");
            vm.setMensaje("Reunión iniciada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encargado/panel.jsp", vm);
    }

    public void finalizar_post() {
        VMReunionMantenimiento vm = (VMReunionMantenimiento) cargarModelo(new VMReunionMantenimiento());
        try {
            if (vm.getResoluciones().isEmpty()) {
                throw new Exception("Debe ingresar las resoluciones de la reunión.");
            }

            DTReunion r = new DTReunion();
            r.setId(Integer.parseInt(vm.getId()));
            r.setObservaciones(vm.getObservaciones());
            r.setResoluciones(Arrays.asList(vm.getResoluciones().split("\n")));
            cliente.finalizarReunion(r);
            vm.setEstado("Finalizada");
            //redireccionar a ver reunión
            vm.setMensaje("Reunión finalizada exitosamente");
        } catch (Exception e) {
            vm.setMensaje(e.getMessage());
        }
        mostrarVista("Encargado/panel.jsp", vm);
    }

}
